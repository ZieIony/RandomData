package tk.zielony.randomdata;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import tk.zielony.randomdata.annotation.Ignore;
import tk.zielony.randomdata.annotation.RandomSize;

public class RandomData {

    private static class GeneratorWithType<Type> {
        final Class<Type> generatedClass;
        final Generator<Type> generator;

        public GeneratorWithType(Class<Type> generatedClass, Generator<Type> generator) {
            this.generatedClass = generatedClass;
            this.generator = generator;
        }
    }

    private List<GeneratorWithType> generators = new ArrayList<>();
    private Map<Class, OnObjectGeneratedListener> postListeners = new HashMap<>();
    private Random random = new Random();

    public RandomData() {
        addGenerator(boolean.class, () -> false);
        addGenerator(int.class, () -> 0);
        addGenerator(float.class, () -> 0.0f);
        addGenerator(String.class, () -> "");
        addGenerator(Drawable.class, ShapeDrawable::new);
        addGenerator(Date.class, Date::new);
        addGenerator(List.class, ArrayList::new);
        addGenerator(Map.class, HashMap::new);
        addGenerator(Set.class, HashSet::new);
    }

    /**
     * Add a generator to generate values for fields.
     * The field type doesn't have to exactly match the provided type - it has to be 'assignable'.
     * For example Float generators will work fine with float and Double fields.
     * Generators work for constructor parameters and writable, non-static fields.
     *
     * @param aClass    a type to handle using the provided generator
     * @param generator a generator
     * @param <Type>
     */
    public <Type> void addGenerator(@NonNull Class<Type> aClass, @NonNull Generator<Type> generator) {
        generators.add(0, new GeneratorWithType<>(aClass, generator));
    }

    /**
     * Add a simple generator to generate values for fields.
     * The field type doesn't have to exactly match the provided type - it has to be 'assignable'.
     * For example Float generators will work fine with float and Double fields.
     * Generators work for constructor parameters and writable, non-static fields.
     *
     * @param aClass    a type to handle using the provided generator
     * @param generator a generator
     * @param <Type>
     */
    public <Type> void addGenerator(@NonNull Class<Type> aClass, @NonNull SimpleGenerator<Type> generator) {
        generators.add(0, new GeneratorWithType<>(aClass, new Generator<Type>() {
            @Override
            public Type next(@Nullable DataContext context) {
                return generator.next();
            }
        }));
    }

    private <Type> Type makeInstance(@NonNull Class<Type> aClass, DataContext context) throws InstantiationException {
        try {
            return aClass.newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
        }
        List<Constructor> constructors = new ArrayList<>(Arrays.asList(aClass.getConstructors()));
        Collections.sort(constructors, (o1, o2) -> o2.getParameterTypes().length - o1.getParameterTypes().length);
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            Target[] parameters = new Target[parameterTypes.length];
            Annotation[][] annotations = constructor.getParameterAnnotations();
            for (int i = 0; i < parameterTypes.length; i++)
                parameters[i] = new Target(annotations[i], parameterTypes[i], aClass);
            try {
                Object[] params = new Object[parameters.length];
                for (int i = 0; i < params.length; i++) {
                    Target target = parameters[i];
                    for (GeneratorWithType g : generators) {
                        if (ClassUtils.isAssignable(g.generatedClass, target.getType(), true) && g.generator.match(target)) {
                            params[i] = g.generator.next(context);
                            break;
                        }
                    }
                }
                return (Type) constructor.newInstance(params);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }

        throw new InstantiationException("Unable to instantiate an object of type " + aClass.getName());
    }

    /**
     * Generate an object using provided generators and parameter factories. Non-parameterless constructors
     * are supported. RandomData fills all non-final and non-static fields using reflection. Constructors
     * are used in the following order: 0-parameter, data, first longest, other
     *
     * @param aClass a type of an object to generate
     * @param <Type>
     * @return the generated object
     */
    @NonNull
    public <Type> Type generate(@NonNull Class<Type> aClass) {
        return generate(aClass, new DataContext());
    }

    @NonNull
    public <Type> Type generate(@NonNull Class<Type> aClass, DataContext context) {
        Type instance;
        try {
            instance = makeInstance(aClass, context);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        fill(instance, context);
        OnObjectGeneratedListener<Type> listener2 = postListeners.get(aClass);
        if (listener2 != null)
            listener2.onObjectGenerated(instance);
        return instance;
    }

    public <Type> Type[] generateArray(Class<Type> aClass, int size) {
        return generateArray(aClass, size, new DataContext());
    }

    public <Type> Type[] generateArray(Class<Type> aClass, int size, DataContext context) {
        Type[] array = (Type[]) Array.newInstance(aClass, size);
        fillArray(array, context);
        return array;
    }

    public <Type> List<Type> generateList(Class<Type> aClass, int size) {
        return generateList(aClass, size, new DataContext());
    }

    public <Type> List<Type> generateList(Class<Type> aClass, int size, DataContext context) {
        List<Type> list = new ArrayList<>();
        for (int i = 0; i < size; i++)
            list.add(generate(aClass, context));
        return list;
    }

    public void fill(@NonNull Object target) {
        fill(target, new DataContext());
    }

    public void fill(@NonNull Object target, DataContext context) {
        context.save();
        if (target.getClass().isArray()) {
            fillArray((Object[]) target, context);
        } else if (List.class.isAssignableFrom(target.getClass())) {
            fillIterable((List) target, context);
        } else if (!(target instanceof Iterable)) {
            fillObject(target, context);
        }
        context.restore();
    }

    private void fillObject(Object target, DataContext context) {
        Class c = target.getClass();
        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            if ((f.getModifiers() & Modifier.FINAL) != 0)
                continue;
            if ((f.getModifiers() & Modifier.STATIC) != 0)
                continue;
            if (f.getAnnotation(Ignore.class) == null)
                fillValue(target, f, context);
        }
    }

    private void fillValue(Object obj, Field field, DataContext context) {
        Target target = new Target(field);
        if (field.getType().isArray()) {
            RandomSize annotation = field.getAnnotation(RandomSize.class);
            int size;
            if (annotation != null) {
                size = random.nextInt(annotation.max() - annotation.min()) + annotation.min();
            } else {
                size = random.nextInt(RandomSize.DEFAULT_MAX - RandomSize.DEFAULT_MIN) + RandomSize.DEFAULT_MIN;
            }
            try {
                field.set(obj, generateArray(target.getType().getComponentType(), size, context));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (target.getType().isAssignableFrom(List.class)) {
            RandomSize annotation = field.getAnnotation(RandomSize.class);
            int size;
            if (annotation != null) {
                size = random.nextInt(annotation.max() - annotation.min()) + annotation.min();
            } else {
                size = random.nextInt(RandomSize.DEFAULT_MAX - RandomSize.DEFAULT_MIN) + RandomSize.DEFAULT_MIN;
            }
            try {
                field.set(obj, generateList(target.getComponentType(), size, context));
            } catch (IllegalAccessException | NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            for (GeneratorWithType g : generators) {
                if (ClassUtils.isAssignable(g.generatedClass, target.getType(), true) && g.generator.match(target)) {
                    try {
                        field.set(obj, g.generator.next(context));
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (target.getType().isPrimitive() || target.getType().equals(String.class))
                return;
            try {
                field.set(obj, generate(target.getType(), context));
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                Object object = field.get(obj);
                if (object != null)
                    fill(object, context);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillArray(Object[] target, DataContext context) {
        for (int i = 0; i < target.length; i++) {
            if (target[i] == null) {
                target[i] = generate(target.getClass().getComponentType(), context);
            } else {
                fill(target[i], context);
            }
        }
    }

    private void fillIterable(Iterable target, DataContext context) {
        for (Object o : target)
            fill(o, context);
    }

    public void reset() {
        for (GeneratorWithType g : generators)
            g.generator.reset();
    }

    public <Type> void addOnObjectGeneratedListener(Class<Type> aClass, OnObjectGeneratedListener listener) {
        postListeners.put(aClass, listener);
    }

    public <Type> void removeOnObjectGeneratedListener(Class<Type> aClass) {
        postListeners.remove(aClass);
    }
}

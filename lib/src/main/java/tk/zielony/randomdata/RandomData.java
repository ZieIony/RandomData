package tk.zielony.randomdata;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tk.zielony.randomdata.annotation.GenerateType;
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
    private Map<Class, ParameterFactory> factories = new HashMap<>();
    private Map<Class, OnObjectGeneratedListener> postListeners = new HashMap<>();
    private Random random = new Random();

    public RandomData() {
        factories.put(String.class, () -> "");
        factories.put(Drawable.class, ShapeDrawable::new);
        factories.put(Date.class, Date::new);
    }

    /**
     * Add a generator to generate values for fields.
     * The field type doesn't have to exactly match the provided type - it has to be 'assignable'.
     * For example Float generators will work fine with float and Double fields.
     *
     * @param aClass    a type to handle using the provided generator
     * @param generator a generator
     * @param <Type>
     */
    public <Type> void addGenerator(@NonNull Class<Type> aClass, @NonNull Generator<Type> generator) {
        generators.add(new GeneratorWithType(aClass, generator));
        if (generator.usableAsFactory() && !factories.containsKey(aClass))
            setParameterFactory(aClass, generator::next);
    }

    /**
     * Set a factory used to provide default (empty) values for non-parameterless constructors.
     * The parameter type doesn't have to exactly match the provided type - it has to be 'assignable'.
     * For example Float factories will work fine with float and Double parameters.
     *
     * @param aClass           a type to handle using the provided parameterFactory
     * @param parameterFactory a parameter factory
     * @param <Type>
     */
    public <Type> void setParameterFactory(@NonNull Class<Type> aClass, @NonNull ParameterFactory<Type> parameterFactory) {
        factories.put(aClass, parameterFactory);
    }

    /**
     * Generate an object using provided generators and parameter factories. Non-parameterless constructors
     * are supported. RandomData fills all non-final and non-static fields using reflection.
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
        Type instance = null;
        try {
            instance = aClass.newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
            Constructor<?>[] constructors = aClass.getConstructors();
            for (Constructor constructor : constructors) {
                Object[] params = new Object[constructor.getParameterTypes().length];
                for (int i = 0; i < params.length; i++) {
                    try {
                        params[i] = generate(constructor.getParameterTypes()[i]);
                    } catch (Exception ex) {
                        params[i] = null;
                        for (Class c : factories.keySet()) {
                            if (ClassUtils.isAssignable(c, constructor.getParameterTypes()[i], true)) {
                                params[i] = factories.get(c).make();
                                break;
                            }
                        }
                    }
                }
                try {
                    instance = (Type) constructor.newInstance(params);
                    break;
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (instance == null)
            throw new RuntimeException("Unable to instantiate an object of type " + aClass.getName());

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

    private void fillValue(Object target, Field f, DataContext context) {
        if (f.getType().isArray()) {
            RandomSize annotation = f.getAnnotation(RandomSize.class);
            int size;
            if (annotation != null) {
                size = random.nextInt(annotation.max() - annotation.min()) + annotation.min();
            } else {
                size = random.nextInt(RandomSize.DEFAULT_MAX - RandomSize.DEFAULT_MIN) + RandomSize.DEFAULT_MIN;
            }
            try {
                f.set(target, generateArray(f.getType().getComponentType(), size, context));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (f.getType().isAssignableFrom(List.class)) {
            RandomSize annotation = f.getAnnotation(RandomSize.class);
            int size;
            if (annotation != null) {
                size = random.nextInt(annotation.max() - annotation.min()) + annotation.min();
            } else {
                size = random.nextInt(RandomSize.DEFAULT_MAX - RandomSize.DEFAULT_MIN) + RandomSize.DEFAULT_MIN;
            }
            try {
                Class typeClass = (Class) ((ParameterizedType) f.getType().getGenericSuperclass()).getActualTypeArguments()[0];
                f.set(target, generateList(typeClass, size, context));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            for (GeneratorWithType g : generators) {
                if (ClassUtils.isAssignable(g.generatedClass, f.getType(), true) && g.generator.match(f)) {
                    try {
                        f.set(target, g.generator.next(context));
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (f.getType().isPrimitive() || f.getType().equals(String.class))
                return;
            try {
                GenerateType annotation = f.getAnnotation(GenerateType.class);
                Class type;
                if (annotation != null) {
                    type = annotation.type();
                } else {
                    type = f.getType();
                }
                f.set(target, generate(type, context));
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                Object object = f.get(target);
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

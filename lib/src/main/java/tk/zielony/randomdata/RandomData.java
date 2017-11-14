package tk.zielony.randomdata;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import tk.zielony.randomdata.annotation.GenerateType;
import tk.zielony.randomdata.annotation.Ignore;
import tk.zielony.randomdata.annotation.RandomSize;

public class RandomData {

    private static ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

    private static class GeneratorWithPriority implements Comparable<GeneratorWithPriority> {
        Generator generator;
        Priority priority;

        GeneratorWithPriority(Generator generator, Priority priority) {
            this.generator = generator;
            this.priority = priority;
        }

        @Override
        public int compareTo(@NonNull GeneratorWithPriority generatorWithPriority) {
            return generatorWithPriority.priority.getValue() - priority.getValue();
        }
    }

    private List<GeneratorWithPriority> generators = new ArrayList<>();
    private Map<Class, OnGenerateObjectListener> preListeners = new HashMap<>();
    private Map<Class, OnObjectGeneratedListener> postListeners = new HashMap<>();
    private Random random = new Random();

    public void addGenerators(Generator[] generators) {
        for (Generator g : generators)
            this.generators.add(new GeneratorWithPriority(g, Priority.Normal));
        Collections.sort(this.generators);
    }

    public void addGenerators(Generator[] generators, Priority priority) {
        for (Generator g : generators)
            this.generators.add(new GeneratorWithPriority(g, priority));
        Collections.sort(this.generators);
    }

    public void addGenerator(Generator generator) {
        generators.add(new GeneratorWithPriority(generator, Priority.Normal));
        Collections.sort(this.generators);
    }

    public void addGenerator(Generator generator, Priority priority) {
        generators.add(new GeneratorWithPriority(generator, priority));
        Collections.sort(this.generators);
    }

    public <Type> Type generate(Class<Type> aClass) {
        return generate(aClass, new DataContext());
    }

    public <Type> Type generate(Class<Type> aClass, DataContext context) {
        try {
            OnGenerateObjectListener listener = preListeners.get(aClass);
            if (listener != null)
                listener.onGenerateObject();
            Type instance = aClass.newInstance();
            fill(instance, context);
            OnObjectGeneratedListener<Type> listener2 = postListeners.get(aClass);
            if (listener2 != null)
                listener2.onObjectGenerated(instance);
            return instance;
        } catch (InstantiationException e) {
            Log.e("RandomData", "No 0-argument constructor or an exception during object construction of type " + aClass.getName());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
        } else if (target.getClass().isAssignableFrom(List.class)) {
            fillList((List) target, context);
        } else if (!(target instanceof Iterable)) {
            fillObject(target, context);
        }
        context.restore();
    }

    private void fillObject(Object target, DataContext context) {
        Class c = target.getClass();
        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
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
            for (GeneratorWithPriority g : generators) {
                if (g.generator.match(f)) {
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

    private void fillList(Iterable target, DataContext context) {
        for (Object o : target)
            fill(o, context);
    }

    public void fillAsync(@NonNull Object target, @Nullable OnFillCompletedListener listener) {
        executor.execute(() -> {
            fill(target);
            if (listener != null)
                listener.onFillCompleted();
        });
    }

    public void reset() {
        for (GeneratorWithPriority g : generators)
            g.generator.reset();
    }

    public <Type> void reset(Class<Type> aClass) {
        for (GeneratorWithPriority g : generators)
            if (g.generator.getClass() == aClass)
                g.generator.reset();
    }

    public <Type> void addOnGenerateObjectListener(Class<Type> aClass, OnGenerateObjectListener listener) {
        preListeners.put(aClass, listener);
    }

    public <Type> void removeOnGenerateObjectListener(Class<Type> aClass) {
        preListeners.remove(aClass);
    }

    public <Type> void addOnObjectGeneratedListener(Class<Type> aClass, OnObjectGeneratedListener listener) {
        postListeners.put(aClass, listener);
    }

    public <Type> void removeOnObjectGeneratedListener(Class<Type> aClass) {
        postListeners.remove(aClass);
    }
}

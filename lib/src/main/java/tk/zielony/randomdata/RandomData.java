package tk.zielony.randomdata;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

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
    private String userInput = null;

    public void addGenerators(Generator[] generators) {
        for (Generator g : generators)
            this.generators.add(new GeneratorWithPriority(g, Priority.Normal));
    }

    public void addGenerators(Generator[] generators, String userInput) {
        this.userInput = userInput;
        for (Generator g : generators)
            this.generators.add(new GeneratorWithPriority(g, Priority.Normal));
    }

    public void addGenerators(Generator[] generators, Priority priority) {
        for (Generator g : generators)
            this.generators.add(new GeneratorWithPriority(g, priority));
    }

    public void addGenerators(Generator[] generators, String userInput, Priority priority) {
        this.userInput = userInput;
        for (Generator g : generators)
            this.generators.add(new GeneratorWithPriority(g, priority));
    }

    public void addGenerator(Generator generator) {
        generators.add(new GeneratorWithPriority(generator, Priority.Normal));
    }

    public void addGenerator(Generator generator, String userInput) {
        this.userInput = userInput;
        generators.add(new GeneratorWithPriority(generator, Priority.Normal));
    }

    public void addGenerator(Generator generator, Priority priority) {
        generators.add(new GeneratorWithPriority(generator, priority));
    }

    public void addGenerator(Generator generator, String userInput, Priority priority) {
        this.userInput = userInput;
        generators.add(new GeneratorWithPriority(generator, priority));
    }

    public void fillAsync(@NonNull Object target, @Nullable OnFillCompletedListener listener) {
        executor.execute(() -> {
            fill(target);
            if (listener != null)
                listener.onFillCompleted();
        });
    }

    public void fill(@NonNull Object target) {
        fill(target, new DataContext());
    }

    public void fill(@NonNull Object target, DataContext context) {
        Collections.sort(generators);
        if (target.getClass().isArray()) {
            fillArray((Object[]) target, context);
        } else if (target instanceof Iterable) {
            fillIterable((Iterable) target, context);
        } else {
            context.save();
            Class c = target.getClass();
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                fillValue(target, f, context);
            }
            context.restore();
        }
    }

    private void fillValue(Object target, Field f, DataContext context) {
        for (GeneratorWithPriority g : generators) {
            if (g.generator.match(f)) {
                try {
                    f.set(target, g.generator.next(context, userInput));
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (f.getType().isPrimitive())
            return;
        try {
            Object o = f.getType().newInstance();
            f.set(target, o);
            fill(o);
        } catch (InstantiationException e) {
            Log.e("RandomData", "No 0-argument constructor or an exception during object construction of type " + f.getType().getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void fillArray(Object[] target, DataContext context) {
        for (int i = 0; i < target.length; i++) {
            if (target[i] == null) {
                try {
                    target[i] = target.getClass().getComponentType().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            fill(target[i], context);
        }
    }

    private void fillIterable(Iterable target, DataContext context) {
        for (Object o : target)
            fill(o, context);
    }

    public void reset() {
        for (GeneratorWithPriority g : generators)
            g.generator.reset();
    }
}

package tk.zielony.randomdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GeneratorWithDuplicates<Type> extends Generator<Type> {
    private Random random = new Random();
    private int total;
    private boolean preventDuplicates;

    private List<Integer> used, free;

    public GeneratorWithDuplicates(int total) {
        this.total = total;
        preventDuplicates = true;
        used = new ArrayList<>();
        free = new ArrayList<>();
        reset();
    }

    public GeneratorWithDuplicates(int total, boolean preventDuplicates) {
        this.total = total;
        this.preventDuplicates = preventDuplicates;
        if (preventDuplicates) {
            used = new ArrayList<>();
            free = new ArrayList<>();
            reset();
        }
    }

    protected int nextIndex() {
        int index;
        if (preventDuplicates) {
            if (free.isEmpty()) {
                List<Integer> swap = free;
                free = used;
                used = swap;
            }
            index = free.get(random.nextInt(free.size()));
            used.add(index);
        } else {
            index = random.nextInt(total);
        }
        return index;
    }

    @Override
    public void reset() {
        if (!preventDuplicates)
            return;
        for (int i = 0; i < total; i++)
            free.add(i);
    }
}

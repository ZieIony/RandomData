package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class IntegerGenerator extends Generator<Integer> {
    private Random random = new Random();
    private int min;
    private int max;
    private int[] array;

    public IntegerGenerator() {
        min = 0;
        max = 100;
    }

    public IntegerGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public IntegerGenerator(int[] array) {
        this.array = array;
    }

    @Override
    public Integer next(DataContext context) {
        return array != null ? array[random.nextInt(array.length)] : random.nextInt(max + 1 - min) + min;
    }
}

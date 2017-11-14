package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class FloatGenerator extends Generator<Float> {
    private Random random = new Random();
    private float min;
    private float max;
    private float[] array;

    public FloatGenerator() {
        min = 0;
        max = 100;
    }

    public FloatGenerator(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public FloatGenerator(float[] array) {
        this.array = array;
    }

    @Override
    public Float nextInternal(DataContext context) {
        return array != null ? array[random.nextInt(array.length)] : random.nextFloat() * (max - min) + min;
    }
}

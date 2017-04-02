package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

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
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(float.class) || f.getType().equals(Float.class) ||
                f.getType().equals(double.class) || f.getType().equals(Double.class);
    }

    @Override
    public Float next(DataContext context, String userInput) {
        return array != null ? array[random.nextInt(array.length)] : random.nextFloat() * (max - min) + min;
    }
}

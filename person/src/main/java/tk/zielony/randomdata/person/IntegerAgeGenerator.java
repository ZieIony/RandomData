package tk.zielony.randomdata.person;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class IntegerAgeGenerator extends Generator<Integer> {
    private Random random = new Random();
    private int min, max;

    public IntegerAgeGenerator() {
        min = 15;
        max = 65;
    }

    public IntegerAgeGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().contains("age");
    }

    @Override
    public Integer nextInternal(DataContext context) {
        return random.nextInt(max - min) + min;
    }
}

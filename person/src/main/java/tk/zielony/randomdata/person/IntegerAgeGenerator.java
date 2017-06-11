package tk.zielony.randomdata.person;

import java.lang.reflect.Field;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
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
        return new Matcher() {
            @Override
            public boolean matches(Field f) {
                return (f.getType().equals(int.class) || f.getType().equals(Integer.class)) && f.getName().contains("age");
            }
        };
    }

    @Override
    public Integer next(DataContext context) {
        return random.nextInt(max - min) + min;
    }
}

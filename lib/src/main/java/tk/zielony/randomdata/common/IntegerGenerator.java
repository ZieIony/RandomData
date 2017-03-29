package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class IntegerGenerator extends Generator<Boolean> {
    private Random random = new Random();
    private final int min;
    private final int max;

    public IntegerGenerator() {
        min = 0;
        max = 100;
    }

    public IntegerGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(int.class) || f.getType().equals(Integer.class)) || f.getType().equals(Integer.class);
    }

    @Override
    public Boolean next(DataContext context) {
        return random.nextBoolean();
    }
}

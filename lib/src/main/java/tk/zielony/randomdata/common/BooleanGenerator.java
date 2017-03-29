package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class BooleanGenerator extends Generator<Boolean> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(boolean.class) || f.getType().equals(Boolean.class)) || f.getType().equals(Boolean.class);
    }

    @Override
    public Boolean next(DataContext context) {
        return random.nextBoolean();
    }
}

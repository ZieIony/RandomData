package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class LongDateGenerator extends Generator<Long> {
    private Random random = new Random();
    private static final int WEEK = 7 * 24 * 60 * 60 * 1000;

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(long.class) || f.getType().equals(Long.class)) && f.getName().equals("date");
    }

    @Override
    public Long nextInternal(DataContext context) {
        return System.currentTimeMillis() - random.nextInt(WEEK);
    }
}

package tk.zielony.randomdata.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class StringDateGenerator extends Generator<String> {
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm, dd MMM");
    private Random random = new Random();
    private static final int WEEK = 7 * 24 * 60 * 60 * 1000;

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().equals("date");
    }

    @Override
    public String next(DataContext context) {
        return format.format(new Date(System.currentTimeMillis() - random.nextInt(WEEK)));
    }
}

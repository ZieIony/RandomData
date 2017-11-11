package tk.zielony.randomdata.common;

import java.util.Date;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class DateGenerator extends Generator<Date> {
    private Random random = new Random();
    private static final int WEEK = 7 * 24 * 60 * 60 * 1000;
    private long startDate;
    private long endDate;

    public DateGenerator() {
        endDate = new Date().getTime();
        startDate = endDate - WEEK;
    }

    public DateGenerator(Date startDate, Date endDate) {
        this.startDate = Math.min(startDate.getTime(), endDate.getTime());
        this.endDate = Math.max(startDate.getTime(), endDate.getTime());
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().equals("date");
    }

    @Override
    public Date next(DataContext context) {
        return new Date(startDate + random.nextInt((int) (endDate - startDate)));
    }
}

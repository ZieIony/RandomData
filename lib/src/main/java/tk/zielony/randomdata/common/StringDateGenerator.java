package tk.zielony.randomdata.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class StringDateGenerator extends Generator<String> {
    private SimpleDateFormat format;
    private Random random = new Random();
    private static final int WEEK = 7 * 24 * 60 * 60 * 1000;
    private long startDate;
    private long endDate;

    public StringDateGenerator() {
        endDate = new Date().getTime();
        startDate = endDate - WEEK;
        format = new SimpleDateFormat("HH:mm, dd MMM");
    }

    public StringDateGenerator(Date startDate, Date endDate, SimpleDateFormat format) {
        this.startDate = Math.min(startDate.getTime(), endDate.getTime());
        this.endDate = Math.max(startDate.getTime(), endDate.getTime());
        this.format = format;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().equals("date");
    }

    @Override
    public String nextInternal(DataContext context) {
        return format.format(startDate + random.nextInt((int) (endDate - startDate)));
    }
}

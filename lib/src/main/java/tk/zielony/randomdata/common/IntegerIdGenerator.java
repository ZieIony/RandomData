package tk.zielony.randomdata.common;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class IntegerIdGenerator extends Generator<Integer> {
    private int id = 0;
    private int startId;

    public IntegerIdGenerator() {
        startId = 0;
    }

    public IntegerIdGenerator(int startId) {
        this.startId = startId;
        id = startId;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(int.class) || f.getType().equals(Integer.class)) && f.getName().contains("id");
    }

    @Override
    public Integer nextInternal(DataContext context) {
        return id++;
    }

    @Override
    public void reset() {
        id = startId;
    }
}

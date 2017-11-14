package tk.zielony.randomdata.common;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class LongIdGenerator extends Generator<Long> {
    private long id = 0;
    private long startId;

    public LongIdGenerator() {
        startId = 0;
    }

    public LongIdGenerator(long startId) {
        this.startId = startId;
        id = startId;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().equals("id");
    }

    @Override
    public Long nextInternal(DataContext context) {
        return id++;
    }

    @Override
    public void reset() {
        id = startId;
    }
}

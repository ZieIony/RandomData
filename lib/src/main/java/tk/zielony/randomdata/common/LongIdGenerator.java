package tk.zielony.randomdata.common;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class LongIdGenerator extends Generator<Long> {
    private long id = 0;

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(long.class) || f.getType().equals(Long.class)) && f.getName().equals("id");
    }

    @Override
    public Long next(DataContext context) {
        return id++;
    }

    @Override
    public void reset() {
        id = 0;
    }
}

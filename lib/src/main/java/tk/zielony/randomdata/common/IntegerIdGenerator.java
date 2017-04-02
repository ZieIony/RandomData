package tk.zielony.randomdata.common;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class IntegerIdGenerator extends Generator<Integer> {
    private int id = 0;

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(int.class) || f.getType().equals(Integer.class)) && f.getName().contains("id");
    }

    @Override
    public Integer next(DataContext context, String userInput) {
        return id++;
    }

    @Override
    public void reset() {
        id = 0;
    }
}

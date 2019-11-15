package tk.zielony.randomdata.person;

import tk.zielony.randomdata.Matcher;
import tk.zielony.randomdata.common.IntegerGenerator;

public class IntegerAgeGenerator extends IntegerGenerator {
    public IntegerAgeGenerator() {
        super(15, 65);
    }

    public IntegerAgeGenerator(int min, int max) {
        super(min, max);
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().contains("age");
    }
}

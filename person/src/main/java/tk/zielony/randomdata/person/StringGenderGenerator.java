package tk.zielony.randomdata.person;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class StringGenderGenerator extends Generator<String> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().equals("gender") || f.getName().equals("sex");
    }

    @Override
    public String nextInternal(DataContext context) {
        if (context != null && context.get(PersonDataContext.GENDER) != null) {
            return ((Gender) context.get(PersonDataContext.GENDER)).name();
        } else {
            Gender g = random.nextBoolean() ? Gender.Female : Gender.Male;
            if (context != null)
                context.set(PersonDataContext.GENDER, g);
            return g.name();
        }
    }
}

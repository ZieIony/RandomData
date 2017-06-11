package tk.zielony.randomdata.person;

import java.lang.reflect.Field;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 30.03.2017.
 */

public class StringGenderGenerator extends Generator<String> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return new Matcher() {
            @Override
            public boolean matches(Field f) {
                return f.getType().equals(String.class) && (f.getName().equals("gender") || f.getName().equals("sex"));
            }
        };
    }

    @Override
    public String next(DataContext context) {
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

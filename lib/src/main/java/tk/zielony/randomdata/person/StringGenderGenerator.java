package tk.zielony.randomdata.person;

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
        return f -> f.getType().equals(String.class) && (f.getName().equals("gender") || f.getName().equals("sex"));
    }

    @Override
    public String next(DataContext context, String userInput) {
        if (context.getGender() != null) {
            return context.getGender().name();
        } else {
            Gender g = random.nextBoolean() ? Gender.Female : Gender.Male;
            context.setGender(g);
            return g.name();
        }
    }
}

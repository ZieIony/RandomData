package tk.zielony.randomdata.person;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class StringNameGenerator extends Generator<String> {
    private StringFirstNameGenerator firstNameGenerator;
    private StringLastNameGenerator lastNameGenerator;

    public StringNameGenerator() {
        firstNameGenerator = new StringFirstNameGenerator();
        lastNameGenerator = new StringLastNameGenerator();
    }

    public StringNameGenerator(Gender gender) {
        firstNameGenerator = new StringFirstNameGenerator(gender, true, true);
        lastNameGenerator = new StringLastNameGenerator();
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().contains("name");
    }

    @Override
    public String nextInternal(DataContext context) {
        String name = context != null ? context.get(DataContext.NAME) : null;
        if (name == null) {
            name = firstNameGenerator.nextInternal(context) + " " + lastNameGenerator.nextInternal(context);
            if (context != null)
                context.set(DataContext.NAME, name);
        }
        return name;
    }

    @Override
    public void reset() {
        firstNameGenerator.reset();
        lastNameGenerator.reset();
    }

}

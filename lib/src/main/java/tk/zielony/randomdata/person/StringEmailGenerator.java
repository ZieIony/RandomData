package tk.zielony.randomdata.person;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 30.03.2017.
 */

public class StringEmailGenerator extends Generator<String> {
    private StringNameGenerator nameGenerator;
    private Random random = new Random();
    private static String[] domains = {"gmail.com", "yahoo.com", "hotmail.com"};

    public StringEmailGenerator() {
        nameGenerator = new StringNameGenerator();
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && getClass().getName().contains("email");
    }

    @Override
    public String next(DataContext context, String userInput) {
        String name = context.getName();
        if (name == null) {
            name = nameGenerator.next(context, null);
            context.setName(name);
        }
        return name.toLowerCase().replace(" ", ".") + "@" + domains[random.nextInt(domains.length)];
    }
}

package tk.zielony.randomdata.place;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class StringCityGenerator extends Generator<String> {
    private String[] names = {"London", "Zadar", "Warszawa"};

    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().contains("city");
    }

    @Override
    public String next(DataContext context) {
        return names[random.nextInt(names.length)];
    }
}
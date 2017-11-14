package tk.zielony.randomdata.place;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class DoubleLongitudeGenerator extends Generator<Double> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().contains("lon");
    }

    @Override
    public Double nextInternal(DataContext context) {
        return random.nextDouble();
    }
}

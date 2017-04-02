package tk.zielony.randomdata.place;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class DoubleLatitudeGenerator extends Generator<Double> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(double.class) || f.getType().equals(Double.class)) && f.getName().contains("lat");
    }

    @Override
    public Double next(DataContext context, String userInput) {
        return random.nextDouble();
    }
}

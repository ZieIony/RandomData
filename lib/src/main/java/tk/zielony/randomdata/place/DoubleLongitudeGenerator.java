package tk.zielony.randomdata.place;

import java.lang.reflect.Field;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class DoubleLongitudeGenerator extends Generator<Double> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return new Matcher() {
            @Override
            public boolean matches(Field f) {
                return (f.getType().equals(double.class) || f.getType().equals(Double.class)) && f.getName().contains("lon");
            }
        };
    }

    @Override
    public Double next(DataContext context) {
        return random.nextDouble();
    }
}

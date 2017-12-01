package tk.zielony.randomdata.util;

import android.support.annotation.Nullable;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

/**
 * Created by kirinpatel on 12/1/17.
 */

public class StringCarGenerator extends Generator<String> {
    private Random random = new Random();

    @Override
    public String nextInternal(@Nullable DataContext context) {
        int numOfCarTypes = Car.class.getEnumConstants().length;
        Car c = Car.class.getEnumConstants()[random.nextInt(numOfCarTypes)];
        return c.name();
    }
}

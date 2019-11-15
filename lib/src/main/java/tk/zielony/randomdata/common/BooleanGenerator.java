package tk.zielony.randomdata.common;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class BooleanGenerator extends Generator<Boolean> {
    private Random random = new Random();

    @Override
    public Boolean next(DataContext context) {
        return random.nextBoolean();
    }

    @Override
    public boolean usableAsFactory() {
        return true;
    }
}

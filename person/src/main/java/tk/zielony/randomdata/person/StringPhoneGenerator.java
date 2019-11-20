package tk.zielony.randomdata.person;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class StringPhoneGenerator extends Generator<String> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName() != null && f.getName().contains("phone");
    }

    // TODO: support country code stored in DataContext
    @Override
    public String next(DataContext context) {
        return "" + (random.nextInt(900000000) + 1000000000);
    }
}

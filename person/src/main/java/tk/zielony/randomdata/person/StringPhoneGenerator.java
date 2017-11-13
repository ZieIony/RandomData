package tk.zielony.randomdata.person;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class StringPhoneGenerator extends Generator<String> {
    private Random random = new Random();

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(int.class) || f.getType().equals(Integer.class)) && f.getName().contains("phone");
    }

    @Override
    public String nextInternal(DataContext context) {
        return ""+(random.nextInt(900000000) + 1000000000);
    }
}

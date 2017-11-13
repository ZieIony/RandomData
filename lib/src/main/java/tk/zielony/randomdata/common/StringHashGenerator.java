package tk.zielony.randomdata.common;

import java.lang.reflect.Field;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class StringHashGenerator extends Generator<String> {
    private Random random = new Random();
    private int length;

    public StringHashGenerator() {
        length = 32;
    }

    public StringHashGenerator(int length) {
        this.length = length;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return new Matcher() {
            @Override
            public boolean matches(Field f) {
                return f.getType().equals(String.class) && f.getName().contains("hash");
            }
        };
    }

    @Override
    public String nextInternal(DataContext context) {
        char[] hash = new char[length];
        for (int i = 0; i < hash.length; i++)
            hash[i] = (char) (random.nextBoolean() ? random.nextInt('9' - '0') + '0' : random.nextInt('z' - 'a') + 'a');
        return new String(hash);
    }
}

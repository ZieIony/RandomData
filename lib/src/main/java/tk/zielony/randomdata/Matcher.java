package tk.zielony.randomdata;

import java.lang.reflect.Field;

/**
 * Created by Marcin on 2017-03-28.
 */

public interface Matcher {
    boolean matches(Field f);
}

package tk.zielony.randomdata;

import java.lang.reflect.Field;

public interface Matcher {
    boolean matches(Field f);
}

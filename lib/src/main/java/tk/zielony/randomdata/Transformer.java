package tk.zielony.randomdata;

/**
 * Created by Marcin on 2017-11-13.
 */

interface Transformer<Type> {
    Type transform(Type value);
}

package tk.zielony.randomdata;

public interface Transformer<Type> {
    Type transform(Type value);
}

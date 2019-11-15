package tk.zielony.randomdata.transformer;

import tk.zielony.randomdata.Transformer;

public class TypeToStringTransformer<Type> implements Transformer<Type, String> {

    @Override
    public String transform(Type value) {
        return value.toString();
    }
}

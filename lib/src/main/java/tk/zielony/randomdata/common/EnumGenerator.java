package tk.zielony.randomdata.common;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class EnumGenerator<Type extends Enum<Type>> extends Generator<Type> {
    private Class<Type> type;
    private Random random = new Random();

    public EnumGenerator() {
        type = (Class<Type>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Type nextInternal(@Nullable DataContext context) {
        return type.getEnumConstants()[random.nextInt(type.getEnumConstants().length)];
    }
}

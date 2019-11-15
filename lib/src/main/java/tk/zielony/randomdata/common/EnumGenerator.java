package tk.zielony.randomdata.common;

import androidx.annotation.Nullable;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class EnumGenerator<Type extends Enum<Type>> extends Generator<Type> {
    private Class<Type> type;
    private Random random = new Random();

    public EnumGenerator(Class<Type> type) {
        this.type = type;
    }

    @Override
    public Type next(@Nullable DataContext context) {
        return type.getEnumConstants()[random.nextInt(type.getEnumConstants().length)];
    }

    @Override
    public boolean usableAsFactory() {
        return true;
    }
}

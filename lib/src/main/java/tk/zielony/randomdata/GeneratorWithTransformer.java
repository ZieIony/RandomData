package tk.zielony.randomdata;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

public class GeneratorWithTransformer<InType, OutType> extends Generator<OutType> {
    private Generator<InType> generator;
    private Transformer<InType, OutType> transformer;

    public GeneratorWithTransformer(Generator<InType> generator, Transformer<InType, OutType> transformer) {
        this.generator = generator;
        this.transformer = transformer;
    }

    public Class<OutType> getGeneratedClass() {
        return (Class<OutType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public OutType next(@Nullable DataContext context) {
        return transformer.transform(generator.next(context));
    }
}

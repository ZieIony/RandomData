package tk.zielony.randomdata;

import androidx.annotation.Nullable;

public class GeneratorWithTransformer<InType, OutType> extends Generator<OutType> {
    private Generator<InType> generator;
    private Transformer<InType, OutType> transformer;

    public GeneratorWithTransformer(Generator<InType> generator, Transformer<InType, OutType> transformer) {
        this.generator = generator;
        this.transformer = transformer;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return generator.getDefaultMatcher();
    }

    @Override
    public OutType next(@Nullable DataContext context) {
        return transformer.transform(generator.next(context));
    }
}

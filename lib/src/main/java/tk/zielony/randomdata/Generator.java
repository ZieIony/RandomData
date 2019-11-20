package tk.zielony.randomdata;

import androidx.annotation.Nullable;

public abstract class Generator<Type> implements SimpleGenerator<Type> {
    private Matcher matcher;

    protected Matcher getDefaultMatcher() {
        return f -> true;
    }

    public Generator<Type> withMatcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public <OutType> GeneratorWithTransformer<Type, OutType> withTransformer(Transformer<Type, OutType> transformer) {
        return new GeneratorWithTransformer<>(this, transformer);
    }

    protected boolean match(Target target) {
        if (matcher == null)
            matcher = getDefaultMatcher();
        return matcher.matches(target);
    }

    public Type next() {
        return next(null);
    }

    public abstract Type next(@Nullable DataContext context);

    public void reset() {
    }
}

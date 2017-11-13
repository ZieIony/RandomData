package tk.zielony.randomdata;

import android.support.annotation.Nullable;

import java.lang.reflect.Field;

public abstract class Generator<Type> {
    private Matcher matcher;
    private Transformer<Type> transformer;

    protected abstract Matcher getDefaultMatcher();

    public Generator<Type> withMatcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public Generator<Type> withTransformer(Transformer<Type> transformer) {
        this.transformer = transformer;
        return this;
    }

    protected boolean match(Field f) {
        if (matcher == null)
            matcher = getDefaultMatcher();
        return matcher.matches(f);
    }

    public Type next() {
        Type nextValue = nextInternal(null);
        if (transformer != null)
            return transformer.transform(nextValue);
        return nextValue;
    }

    public Type next(@Nullable DataContext context) {
        Type nextValue = nextInternal(context);
        if (transformer != null)
            return transformer.transform(nextValue);
        return nextValue;
    }

    public abstract Type nextInternal(@Nullable DataContext context);

    public void reset() {
    }
}

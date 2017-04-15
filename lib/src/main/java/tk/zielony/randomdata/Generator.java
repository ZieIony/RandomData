package tk.zielony.randomdata;

import android.support.annotation.Nullable;

import java.lang.reflect.Field;

public abstract class Generator<Type> {
    private Matcher matcher;

    protected abstract Matcher getDefaultMatcher();

    public Generator<Type> withMatcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    protected boolean match(Field f) {
        if (matcher == null)
            matcher = getDefaultMatcher();
        return matcher.matches(f);
    }

    public Type next() {
        return next(null);
    }

    public abstract Type next(@Nullable DataContext context);

    public void reset() {
    }
}

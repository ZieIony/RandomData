package tk.zielony.randomdata;

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

    public abstract Type next(DataContext context, String userInput);

    public void reset() {
    }
}

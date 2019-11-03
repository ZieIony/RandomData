package tk.zielony.randomdata;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public abstract class Generator<Type> {
    private Matcher matcher;
    private Transformer<Type> transformer;

    protected Matcher getDefaultMatcher() {
        return f -> true;
    }

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
        Class<Type> typeClass = (Class<Type>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return ClassUtils.isAssignable(typeClass, f.getType(), true) && matcher.matches(f);
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

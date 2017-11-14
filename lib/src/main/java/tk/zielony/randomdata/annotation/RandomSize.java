package tk.zielony.randomdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomSize {
    int min() default DEFAULT_MIN;

    int max() default DEFAULT_MAX;

    int DEFAULT_MIN = 5;
    int DEFAULT_MAX = 10;
}

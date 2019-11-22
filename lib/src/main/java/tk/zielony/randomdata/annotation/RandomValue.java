package tk.zielony.randomdata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomValue {
    String name() default "";

    Class<?> type() default void.class;

    Class<?> componentType() default void.class;
}

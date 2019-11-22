package tk.zielony.randomdata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import tk.zielony.randomdata.annotation.GenerateType;
import tk.zielony.randomdata.annotation.RandomValue;

public class Target {
    private String name;
    private Annotation[] annotations;
    private Class<?> type, componentType;
    private Class owner;

    public Target(Field field) {
        name = field.getName();
        annotations = field.getAnnotations();
        type = field.getType();
        try {
            componentType = (Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
        }
        owner = field.getDeclaringClass();

        for (Annotation a : annotations) {
            if (a instanceof RandomValue) {
                RandomValue rv = (RandomValue) a;
                if (!rv.name().equals(""))
                    name = rv.name();
                if (rv.type() != void.class)
                    type = rv.type();
                if (rv.componentType() != void.class)
                    componentType = rv.componentType();
            } else if (a instanceof GenerateType) {
                type = ((GenerateType) a).type();
            }
        }
    }

    public Target(Annotation[] annotations, Class type, Class owner) {
        this.annotations = annotations;
        this.type = type;
        this.owner = owner;

        for (Annotation a : annotations) {
            if (a instanceof RandomValue) {
                RandomValue rv = (RandomValue) a;
                if (!rv.name().equals(""))
                    name = rv.name();
                if (rv.type() != void.class)
                    this.type = rv.type();
                if (rv.componentType() != void.class)
                    componentType = rv.componentType();
            } else if (a instanceof GenerateType) {
                this.type = ((GenerateType) a).type();
            }
        }
    }

    public String getName() {
        return name;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public Class<?> getType() {
        return type;
    }

    public Class<?> getComponentType() {
        return componentType;
    }

    public Class getDeclaringClass() {
        return owner;
    }
}

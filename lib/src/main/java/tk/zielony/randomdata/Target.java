package tk.zielony.randomdata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Target {
    private String name;
    private Annotation[] annotations;
    private Class type;
    private Class owner;

    public Target(Field field) {
        name = field.getName();
        annotations = field.getAnnotations();
        type = field.getType();
        owner = field.getDeclaringClass();
    }

    public Target(Annotation[] annotations, Class type, Class owner) {
        this.annotations = annotations;
        this.type = type;
        this.owner = owner;
    }

    public Target(String name, Annotation[] annotations, Class type, Class owner) {
        this.name = name;
        this.annotations = annotations;
        this.type = type;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public Class getType() {
        return type;
    }

    public Class getDeclaringClass() {
        return owner;
    }
}

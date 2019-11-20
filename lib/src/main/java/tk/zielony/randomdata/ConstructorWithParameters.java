package tk.zielony.randomdata;

import java.lang.reflect.Constructor;

class ConstructorWithParameters {
    private final Constructor constructor;
    private final Target[] parameters;

    public ConstructorWithParameters(Constructor constructor, Target[] parameters) {
        this.constructor = constructor;
        this.parameters = parameters;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public Target[] getParameters() {
        return parameters;
    }
}

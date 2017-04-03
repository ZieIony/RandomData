package tk.zielony.randomdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataContext {

    private static final String LOCALE = "locale";
    public static final String NAME = "name";

    protected final List<Map<String, Object>> stack = new ArrayList<>();

    void save() {
        HashMap<String, Object> contextData = (HashMap<String, Object>) stack.get(stack.size() - 1);
        HashMap<String, Object> clone = (HashMap<String, Object>) contextData.clone();
        stack.add(clone);
    }

    public DataContext() {
        stack.add(new HashMap<>());
    }

    void restore() {
        stack.remove(stack.size() - 1);
    }

    public <Type> Type get(String key) {
        return (Type) stack.get(stack.size() - 1).get(key);
    }

    public <Type> void set(String key, Type value) {
        stack.get(stack.size() - 1).put(key, value);
    }
}

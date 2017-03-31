package tk.zielony.randomdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tk.zielony.randomdata.person.Gender;

public class DataContext {
    private static class ContextData implements Cloneable {
        Gender gender;
        Locale locale;
        String name;

        @Override
        protected Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException ignored) {
            }
            return null;
        }
    }

    private final List<ContextData> stack = new ArrayList<>();

    void save() {
        ContextData contextData = stack.get(stack.size() - 1);
        ContextData clone = (ContextData) contextData.clone();
        stack.add(clone);
    }

    public DataContext() {
        stack.add(new ContextData());
    }

    void restore() {
        stack.remove(stack.size() - 1);
    }

    public Gender getGender() {
        return stack.get(stack.size() - 1).gender;
    }

    public void setGender(Gender gender) {
        stack.get(stack.size() - 1).gender = gender;
    }

    public Locale getLocale() {
        return stack.get(stack.size() - 1).locale;
    }

    public void setLocale(Locale locale) {
        stack.get(stack.size() - 1).locale = locale;
    }

    public String getName() {
        return stack.get(stack.size() - 1).name;
    }

    public void setName(String name) {
        stack.get(stack.size() - 1).name = name;
    }
}

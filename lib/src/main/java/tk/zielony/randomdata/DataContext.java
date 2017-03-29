package tk.zielony.randomdata;

import java.util.ArrayList;
import java.util.List;

import tk.zielony.randomdata.person.Gender;

/**
 * Created by Marcin on 2017-03-27.
 */

public class DataContext {
    private List<Gender> gender = new ArrayList<>();

    public DataContext() {
        gender.add(null);
    }

    void save() {
        gender.add(gender.get(gender.size() - 1));
    }

    void restore() {
        gender.remove(gender.size() - 1);
    }

    public void setGender(Gender gender) {
        this.gender.set(this.gender.size() - 1, gender);
    }

    public Gender getGender() {
        return gender.get(gender.size() - 1);
    }
}

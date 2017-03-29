package tk.zielony.randomdata;

/**
 * Created by Marcin on 28.03.2017.
 */
public enum Priority {
    Low(1), Normal(2), High(3);

    private int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

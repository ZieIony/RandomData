package tk.zielony.randomdata.finance;

import java.util.Currency;
import java.util.Locale;
import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public abstract class AmountGenerator<Type> extends Generator<Type> {
    private Random random = new Random();
    private float max;
    private boolean useCommon;
    private float[] common = {0.00f, 0.25f, 0.49f, 0.50f, 0.75f, 0.95f, 0.99f};

    public AmountGenerator() {
        max = 1000;
        useCommon = true;
    }

    public AmountGenerator(int max, boolean useCommon) {
        this.max = max;
        this.useCommon = useCommon;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName() != null && f.getName().contains("amount");
    }

    public float next2(DataContext context) {
        Currency currency = Currency.getInstance(Locale.getDefault());
        float cents = 0;
        if (currency.getDefaultFractionDigits() == 2) {
            if (useCommon) {
                cents = common[random.nextInt(common.length)];
            } else {
                cents = (float) random.nextInt(100) / 100;
            }
        }
        return Math.min(max, random.nextInt((int) Math.floor(max + 1)) + cents);
    }
}

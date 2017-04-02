package tk.zielony.randomdata.finance;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class FloatAmountGenerator extends AmountGenerator<Float> {

    public FloatAmountGenerator() {
        super();
    }

    public FloatAmountGenerator(int max, boolean useCommon) {
        super(max, useCommon);
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(float.class) || f.getType().equals(Float.class)) && f.getName().contains("amount");
    }

    @Override
    public Float next(DataContext context, String userInput) {
        return next2(context);
    }
}

package tk.zielony.randomdata.finance;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Matcher;

public class FloatAmountGenerator extends AmountGenerator<Float> {

    public FloatAmountGenerator() {
        super();
    }

    public FloatAmountGenerator(int max, boolean useCommon) {
        super(max, useCommon);
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName() != null && f.getName().contains("amount");
    }

    @Override
    public Float next(DataContext context) {
        return next2(context);
    }
}

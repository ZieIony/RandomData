package tk.zielony.randomdata.finance;

import java.util.Currency;
import java.util.Locale;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class StringAmountGenerator extends AmountGenerator<String> {

    public StringAmountGenerator() {
        super();
    }

    public StringAmountGenerator(int max, boolean useCommon) {
        super(max, useCommon);
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().contains("amount");
    }

    @Override
    public String next(DataContext context, String userInput) {
        Currency currency = Currency.getInstance(Locale.getDefault());
        return String.format(Locale.getDefault(), "%s %.2f", currency.getSymbol(), next2(context));
    }
}

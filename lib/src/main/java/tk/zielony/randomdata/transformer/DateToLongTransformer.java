package tk.zielony.randomdata.transformer;

import java.util.Date;

import tk.zielony.randomdata.Transformer;

public class DateToLongTransformer implements Transformer<Date, Long> {

    @Override
    public Long transform(Date date) {
        return date.getTime();
    }
}

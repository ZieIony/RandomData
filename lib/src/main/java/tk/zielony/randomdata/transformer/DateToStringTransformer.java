package tk.zielony.randomdata.transformer;

import java.text.SimpleDateFormat;
import java.util.Date;

import tk.zielony.randomdata.Transformer;

public class DateToStringTransformer implements Transformer<Date, String> {
    private SimpleDateFormat format;

    public DateToStringTransformer() {
        format = new SimpleDateFormat("HH:mm, dd MMM");
    }

    public DateToStringTransformer(SimpleDateFormat format) {
        this.format = format;
    }

    @Override
    public String transform(Date date) {
        return format.format(date);
    }
}

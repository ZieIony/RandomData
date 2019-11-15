package tk.zielony.randomdata.transformer;


import tk.zielony.randomdata.Transformer;

public class LongToIntegerTransformer implements Transformer<Long, Integer> {

    @Override
    public Integer transform(Long value) {
        return (int) ((long) value);
    }
}

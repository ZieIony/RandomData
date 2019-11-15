package tk.zielony.randomdata;

public interface Transformer<InType, OutType> {
    OutType transform(InType value);
}

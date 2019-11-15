package tk.zielony.randomdata.food;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.GeneratorWithDuplicates;
import tk.zielony.randomdata.Matcher;

public class StringFruitGenerator extends GeneratorWithDuplicates<String> {
    private static String[] fruits = {"Apple", "Apricot", "Avocado", "Banana", "Bilberry", "Blackberry", "Blackcurrant", "Blueberry",
            "Boysenberry", "Currant", "Cherry", "Cherimoya", "Cloudberry", "Coconut", "Cranberry", "Cucumber", "Custard apple",
            "Damson", "Date", "Dragonfruit", "Durian", "Elderberry", "Feijoa", "Fig", "Goji berry", "Gooseberry", "Grape",
            "Raisin", "Grapefruit", "Guava", "Honeyberry", "Huckleberry", "Jabuticaba", "Jackfruit", "Jambul",
            "Jujube", "Juniper berry", "Kiwifruit", "Kumquat", "Lemon", "Lime", "Loquat", "Longan", "Lychee", "Mango",
            "Marionberry", "Melon", "Cantaloupe", "Honeydew", "Watermelon", "Miracle fruit", "Mulberry", "Nectarine",
            "Nance", "Olive", "Orange", "Blood orange", "Clementine", "Mandarine", "Tangerine", "Papaya", "Passionfruit",
            "Peach", "Pear", "Persimmon", "Physalis", "Plantain", "Plum", "Prune", "Pineapple", "Plumcot",
            "Pomegranate", "Pomelo", "Purple mangosteen", "Quince", "Raspberry", "Salmonberry", "Rambutan", "Redcurrant",
            "Salal berry", "Salak", "Satsuma", "Soursop", "Star fruit", "Solanum quitoense", "Strawberry", "Tamarillo",
            "Tamarind", "Ugli fruit", "Yuzu"};

    public StringFruitGenerator() {
        super(fruits.length);
    }

    public StringFruitGenerator(boolean preventDuplicates) {
        super(fruits.length, preventDuplicates);
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().contains("name");
    }

    @Override
    public String next(DataContext context) {
        return fruits[nextIndex()];
    }
}

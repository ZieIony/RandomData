package tk.zielony.randomdata.food;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class StringFruitGenerator extends Generator<String> {
    private String[] fruits = {"Apple", "Apricot", "Avocado", "Banana", "Bilberry", "Blackberry", "Blackcurrant", "Blueberry",
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

    private Random random = new Random();
    private boolean preventDuplicates;

    private Set<String> used = new HashSet<>();

    public StringFruitGenerator() {
        preventDuplicates = true;
    }

    public StringFruitGenerator(boolean preventDuplicates) {
        this.preventDuplicates = preventDuplicates;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().contains("name");
    }

    @Override
    public String nextInternal(DataContext context) {
        String name;
        if (preventDuplicates) {
            if (used.size() == fruits.length)
                used.clear();
            do {
                name = next2();
            }
            while (used.contains(name));
            used.add(name);
        } else {
            name = next2();
        }
        return name;
    }

    private String next2() {
        return fruits[random.nextInt(fruits.length)];
    }

    @Override
    public void reset() {
        used.clear();
    }
}

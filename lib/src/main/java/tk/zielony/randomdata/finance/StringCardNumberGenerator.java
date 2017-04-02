package tk.zielony.randomdata.finance;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class StringCardNumberGenerator extends Generator<String> {
    private static class BIN {
        String name;
        int number;
        int length;

        BIN(String name, int number, int length) {
            this.name = name;
            this.number = number;
            this.length = length;
        }
    }

    private Random random = new Random();

    private static BIN[] bins = {
            new BIN("MasterCard", 51, 16),
            new BIN("MasterCard", 52, 16),
            new BIN("MasterCard", 53, 16),
            new BIN("MasterCard", 54, 16),
            new BIN("MasterCard", 55, 16),
            new BIN("VISA", 4, 16),
            new BIN("American Express", 34, 15),
            new BIN("American Express", 37, 15)
    };

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().contains("number");
    }

    @Override
    public String next(DataContext context, String userInput) {
        BIN bin = bins[random.nextInt(bins.length)];
        StringBuilder builder = new StringBuilder();
        builder.append(bin.number);
        while (builder.length() < bin.length - 1) {
            builder.append(random.nextInt(10));
        }
        builder.append(getLuhnDigit(builder.toString()));
        for (int i = 0; i < bin.length; i += 4)
            builder.insert(bin.length - i, " ");
        if (userInput != null) {
            builder.append(userInput);
        }
        return builder.toString();
    }

    private int getLuhnDigit(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = number.charAt(i) - '0';

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod;
    }
}

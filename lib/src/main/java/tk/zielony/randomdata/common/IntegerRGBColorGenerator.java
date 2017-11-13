package tk.zielony.randomdata.common;

import android.graphics.Color;
import android.support.annotation.NonNull;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class IntegerRGBColorGenerator extends Generator<Integer> {
    private int minA, minR, minG, minB;
    private int maxA, maxR, maxG, maxB;
    private Random random = new Random();
    private boolean grayscale;

    public IntegerRGBColorGenerator() {
        minA = 255;
        minR = 0;
        minG = 0;
        minB = 0;
        maxA = 255;
        maxR = 255;
        maxG = 255;
        maxB = 255;
        grayscale = false;
    }

    /**
     * Both arrays can be 1, 2, 3 or 4 elements long. Depending on the length, arrays will be
     * interpreted as [grayscale], [alpha, grayscale], [r, g, b] or [alpha, r, g, b]
     *
     * @param min
     * @param max
     */
    public IntegerRGBColorGenerator(@NonNull int min[], @NonNull int max[]) {
        if (min.length != max.length)
            throw new IllegalArgumentException("min.length != max.length");
        if (min.length == 0 || min.length > 4)
            throw new IllegalArgumentException("min.length == 0 || min.length > 4");

        grayscale = min.length <= 2;

        minA = min.length == 2 || min.length == 4 ? min[0] : 255;
        minR = min.length == 1 ? min[0] : min[1];
        minG = grayscale ? 0 : min[min.length - 2];
        minB = grayscale ? 0 : min[min.length - 1];
        maxA = max.length == 2 || max.length == 4 ? max[0] : 255;
        maxR = max.length == 1 ? max[0] : max[1];
        maxG = grayscale ? 0 : max[max.length - 2];
        maxB = grayscale ? 0 : max[max.length - 1];
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> (f.getType().equals(int.class) || f.getType().equals(Integer.class)) && f.getName().contains("color");
    }

    @Override
    public Integer nextInternal(DataContext context) {
        if (grayscale) {
            int g = random.nextInt(maxR + 1 - minR) + minR;
            return Color.argb(random.nextInt(maxA + 1 - minA) + minA, g, g, g);
        } else {
            int r = random.nextInt(maxR + 1 - minR) + minR;
            int g = random.nextInt(maxG + 1 - minG) + minG;
            int b = random.nextInt(maxB + 1 - minB) + minB;
            return Color.argb(random.nextInt(maxA + 1 - minA) + minA, r, g, b);
        }
    }
}

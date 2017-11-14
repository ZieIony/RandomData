package tk.zielony.randomdata.common;

import android.graphics.Color;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class IntegerHSVColorGenerator extends Generator<Integer> {
    private int minA, maxA;
    private float minH, minS, minV;
    private float maxH, maxS, maxV;
    private boolean grayscale;
    private Random random = new Random();

    public IntegerHSVColorGenerator() {
        minA = 255;
        minH = 0;
        minS = 0;
        minV = 0;
        maxA = 255;
        maxH = 360;
        maxS = 1;
        maxV = 1;
        grayscale = false;
    }

    /**
     * Both arrays can be 2 or 3 elements long. Depending on the length, arrays will be
     * interpreted as [h, v] or [h, s,v]
     *
     * @param min
     * @param max
     */
    public IntegerHSVColorGenerator(int minA, float min[], int maxA, float max[]) {
        if (min.length != max.length)
            throw new IllegalArgumentException("min.length != max.length");
        if (min.length < 2 || min.length > 3)
            throw new IllegalArgumentException("min.length < 2 || min.length > 3");

        grayscale = min.length == 2;

        this.minA = minA;
        minH = min[0];
        minS = grayscale ? 0 : min[1];
        minV = grayscale ? min[1] : min[2];
        this.maxA = maxA;
        maxH = max[0];
        maxS = grayscale ? 0 : max[1];
        maxV = grayscale ? max[1] : max[2];
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName().contains("color");
    }

    @Override
    public Integer nextInternal(DataContext context) {
        if (grayscale) {
            float h = random.nextFloat() * (maxH - minH) + minH;
            float v = random.nextFloat() * (maxV - minV) + maxV;
            return Color.HSVToColor(random.nextInt(maxA + 1 - minA) + minA, new float[]{h, 0, v});
        } else {
            float h = random.nextFloat() * (maxH - minH) + minH;
            float s = random.nextFloat() * (maxS - minS) + minS;
            float v = random.nextFloat() * (maxV - minV) + maxV;
            return Color.HSVToColor(random.nextInt(maxA + 1 - minA) + minA, new float[]{h, s, v});
        }
    }
}

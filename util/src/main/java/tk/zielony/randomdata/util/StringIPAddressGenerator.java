package tk.zielony.randomdata.util;

import android.support.annotation.Nullable;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class StringIPAddressGenerator extends Generator<String> {

    public enum classRange {
        A, B, C
    }

    private Random random = new Random();
    private classRange range = classRange.A;

    public StringIPAddressGenerator() {

    }

    public StringIPAddressGenerator(classRange range) {
        this.range = range;
    }

    public classRange getClassRange() {
        return range;
    }

    public void setClassRange(classRange range) {
        this.range = range;
    }

    private int generateInitialSection() {
        switch(range) {
            case A:
                return random.nextInt(63) + 128;
            case B:
                return random.nextInt(31) + 192;
            default:
                return random.nextInt(126) + 1;
        }
    }

    private int generateSubSection() {
        return random.nextInt(256);
    }

    @Override
    public String nextInternal(@Nullable DataContext context) {
        return new StringBuilder()
                .append(generateInitialSection())
                .append(".").append(generateSubSection())
                .append(".").append(generateSubSection())
                .append(".").append(generateSubSection()).toString();
    }
}

package tk.zielony.randomdata.util;

import androidx.annotation.Nullable;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

public class StringIPAddressGenerator extends Generator<String> {

    public enum ClassRange {
        A(1, 126), B(128, 191), C(192, 223);

        private int min, max;

        ClassRange(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public int getValue(Random random) {
            return random.nextInt(max - min) + min;
        }
    }

    private Random random = new Random();
    private ClassRange range = ClassRange.A;

    public StringIPAddressGenerator() {
    }

    public StringIPAddressGenerator(ClassRange range) {
        this.range = range;
    }

    public ClassRange getClassRange() {
        return range;
    }

    public void setClassRange(ClassRange range) {
        this.range = range;
    }

    private int generateInitialSection() {
        return range.getValue(random);
    }

    private int generateSubSection() {
        return random.nextInt(256);
    }

    @Override
    public String next(@Nullable DataContext context) {
        return generateInitialSection() +
                "." + generateSubSection() +
                "." + generateSubSection() +
                "." + generateSubSection();
    }
}

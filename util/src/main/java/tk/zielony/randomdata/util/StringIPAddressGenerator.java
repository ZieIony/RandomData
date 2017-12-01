package tk.zielony.randomdata.util;

import android.support.annotation.Nullable;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

/**
 * Created by kirinpatel on 12/1/17.
 */

public class StringIPAddressGenerator extends Generator<String> {
    private Random random = new Random();
    private int classRange;

    StringIPAddressGenerator() {
        classRange = 0;
    }

    public StringIPAddressGenerator(int classRange) {
        this.classRange = classRange;
    }

    public int getClassRange() {
        return classRange;
    }

    public void setClassRange(int classRange) {
        if (classRange >= 0 && classRange < 3) {
            this.classRange = classRange;
        } else if (classRange < 0) {
            this.classRange = 0;
        } else if (classRange > 2) {
            this.classRange = 2;
        }
    }

    /* Values for initial section obtained from
       https://www.neowin.net/forum/topic/127123-lowest-and-highest-ip-address/?do=findComment&comment=1525345 */
    private int generateInitialSection() {
        switch(classRange) {
            case 1:
                return random.nextInt(63) + 128;
            case 2:
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
        return generateInitialSection()
                + "." + generateSubSection()
                + "." + generateSubSection()
                + "." + generateSubSection();
    }
}

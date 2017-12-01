package tk.zielony.randomdata.util;

import android.support.annotation.Nullable;

import java.util.Random;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;

/**
 * Created by kirinpatel on 12/1/17.
 */

public class VersionNumberGenerator extends Generator<String> {
    private Random random = new Random();
    private int minVersion, maxVersion, minBuild, maxBuild;

    VersionNumberGenerator() {
        minVersion = 0;
        maxVersion = 1;
        minBuild = 0;
        maxBuild = 10;
    }

    public VersionNumberGenerator(int minVersion, int maxVersion, int minBuild, int maxBuild) {
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
        this.minBuild = minBuild;
        this.maxBuild = maxBuild;
    }

    public int getMinVersion() {
        return minVersion;
    }

    public int getMaxVersion() {
        return maxVersion;
    }

    public int getMinBuild() {
        return minBuild;
    }

    public int getMaxBuild() {
        return maxBuild;
    }

    public void setMinVersion(int minVersion) {
        this.minVersion = minVersion;
    }

    public void setMaxVersion(int maxVersion) {
        this.maxVersion = maxVersion;
    }

    public void setMinBuild(int minBuild) {
        this.minBuild = minBuild;
    }

    public void setMaxBuild(int maxBuild) {
        this.maxBuild = maxBuild;
    }

    @Override
    public String nextInternal(@Nullable DataContext context) {
        return (random.nextInt(maxVersion - minVersion) + minVersion)
                + "."
                + (random.nextInt(maxBuild - minBuild) + minBuild);
    }

    public int nextVersionNumber(@Nullable DataContext context) {
        return random.nextInt(maxVersion - minVersion) + minVersion;
    }

    public int nextBuildNumber(@Nullable DataContext context) {
        return random.nextInt(maxBuild - minBuild) + minBuild;
    }
}

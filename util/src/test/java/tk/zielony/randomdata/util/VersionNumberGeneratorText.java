package tk.zielony.randomdata.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kirinpatel on 12/1/17.
 */

public class VersionNumberGeneratorText {

    @Test
    public void generationIsCorrect() throws Exception {
        VersionNumberGenerator versionNumberGenerator = new VersionNumberGenerator();
        int version = versionNumberGenerator.nextVersionNumber(null);
        int build = versionNumberGenerator.nextBuildNumber(null);
        assertEquals(true,
                version >= versionNumberGenerator.getMinVersion()
                        && version <= versionNumberGenerator.getMaxVersion());
        assertEquals(true,
                build >= versionNumberGenerator.getMinBuild()
                        && build <= versionNumberGenerator.getMaxBuild());
    }
}

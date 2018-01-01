package tk.zielony.randomdata.util;

import android.util.Log;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StringIPAddressGeneratorTest {

    @Test
    public void generationIsCorrect() throws Exception {
        StringIPAddressGenerator ipAddressGenerator = new StringIPAddressGenerator();
        for (StringIPAddressGenerator.classRange range : StringIPAddressGenerator.classRange.values()) {
            ipAddressGenerator.setClassRange(range);
            String ipAddress = ipAddressGenerator.nextInternal(null);
            int initialSection = Integer.parseInt(ipAddress.substring(0, ipAddress.indexOf('.')));
            switch(range) {
                case A:
                    assertEquals(true, 191 >= initialSection && initialSection >= 128);
                    break;
                case B:
                    assertEquals(true, 223 >= initialSection && initialSection >= 192);
                    break;
                case C:
                    assertEquals(true, 127 >= initialSection && initialSection >= 1);
                    break;
            }
        }
    }
}

package tk.zielony.randomdata.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StringIPAddressGeneratorTest {

    @Test
    public void generationIsCorrect() throws Exception {
        StringIPAddressGenerator ipAddressGenerator = new StringIPAddressGenerator();
        for (StringIPAddressGenerator.ClassRange range : StringIPAddressGenerator.ClassRange.values()) {
            ipAddressGenerator.setClassRange(range);
            String ipAddress = ipAddressGenerator.next();
            int initialSection = Integer.parseInt(ipAddress.substring(0, ipAddress.indexOf('.')));
            assertEquals(true, range.getMax() >= initialSection && initialSection >= range.getMin());
        }
    }
}

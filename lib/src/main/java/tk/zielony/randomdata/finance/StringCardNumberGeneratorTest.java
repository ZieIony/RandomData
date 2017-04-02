package tk.zielony.randomdata;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringCardNumberGeneratorTest {

    StringCardNumberGenerator generator = new StringCardNumberGenerator();

    @Test
    public void userInput() throws Exception {
        String cardNumber = generator.next(new DataContext(), "fake");
        assertTrue(cardNumber.contains("fake"));
    }
}
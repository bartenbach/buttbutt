package net.alureon.ircbutt.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StringUtilsTest {


    private final String[] args = { "x", "y", "z"};

    @Test
    public void testArrayToString() {
        assertEquals(StringUtils.arrayToString(args), "x y z");
    }

    @Test
    public void testGetArgs() {
        assertEquals(StringUtils.getArgs(args), "y z");
    }

    @Test
    public void testGetArgsOverOne() {
        assertEquals(StringUtils.getArgsOverOne(args), "z");
    }

    @Test
    public void testConcatenateUrlArgs() {
        assertEquals(StringUtils.concatenateUrlArgs(args), "y+z");
    }
}

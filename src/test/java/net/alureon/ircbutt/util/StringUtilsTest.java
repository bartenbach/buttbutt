package net.alureon.ircbutt.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StringUtilsTest {


    private final String[] args = { " x", "y", "z "};

    @Test
    public void testArrayToString() {
        assertEquals("x y z", StringUtils.arrayToString(args));
    }

    @Test
    public void testGetArgs() {
        assertEquals("y z", StringUtils.getArgs(args));
    }

    @Test
    public void testGetArgsOverOne() {
        assertEquals("z", StringUtils.getArgsOverOne(args));
    }

}

package net.alureon.ircbutt.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests classes in the StringUtils Class.
 */
public final class StringUtilsTest {


    /**
     * Our test arguments.
     */
    private final String[] args = {" x", "y", "z "};

    /**
     * Tests the arrayToString function.
     */
    @Test
    public void testArrayToString() {
        assertEquals("x y z", StringUtils.arrayToString(args));
    }

    /**
     * Tests the getArgs function.
     */
    @Test
    public void testGetArgs() {
        assertEquals("y z", StringUtils.getArgs(args));
    }

    /**
     * Tests the getArgsOverOne function.
     */
    @Test
    public void testGetArgsOverOne() {
        assertEquals("z", StringUtils.getArgsOverOne(args));
    }

}

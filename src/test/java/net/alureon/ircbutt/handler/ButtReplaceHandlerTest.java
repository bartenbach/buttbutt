package net.alureon.ircbutt.handler;

import org.junit.Test;

/**
 * Tests the butt replace handler.
 */
public final class ButtReplaceHandlerTest {

    /**
     * Tests butt's buttify.
     */
    @Test
    public void testButtReplace() {
        String message = "This is a test message, bitch.";
        ButtReplaceHandler replaceHandler = new ButtReplaceHandler(null);
        String butted = replaceHandler.buttifyMessage(message);
        System.out.println(butted);
        String message2 = "THIS IS A TEST MESSAGE, BITCH.";
        String butted2 = replaceHandler.buttifyMessage(message2);
        System.out.println(butted2);
    }

}

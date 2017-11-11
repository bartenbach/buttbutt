package net.alureon.ircbutt.handler.command;

import org.junit.Assert;
import org.junit.Test;

public class Rot13HandlerTest {

    @Test
    public void testRot13() {
        String result = Rot13Handler.handleRot13("green");
        Assert.assertEquals(result, "terra");
    }

}


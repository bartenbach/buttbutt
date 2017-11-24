package net.alureon.ircbutt.command;

import net.alureon.ircbutt.command.commands.Rot13Command;
import org.junit.Assert;
import org.junit.Test;

public class Rot13CommandTest {

    @Test
    public void testRot13() {
        String result = Rot13Command.handleRot13("green");
        Assert.assertEquals(result, "terra");
    }

}


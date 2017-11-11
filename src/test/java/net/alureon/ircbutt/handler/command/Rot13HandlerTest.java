package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.handler.CommandHandler;
import org.junit.Assert;
import org.junit.Test;

public class Rot13HandlerTest {

    @Test
    public void testRot13() {
        String result = Rot13Handler.handleRot13(null, "green");
        Assert.assertEquals(result, "terra");
    }

    @Test
    public void testRot13OfUser() {
        String input = "Hello $USER!";
        String translated = CommandHandler.parseCommandSubstitutionAndVariables(null, null, input, "alureon");
        Assert.assertEquals(translated, "Hello alureon!");
    }
}

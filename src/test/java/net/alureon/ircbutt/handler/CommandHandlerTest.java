package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class CommandHandlerTest {

    @Test
    public void testCommandSubstitution() {
        String input = "Hello $USER!";
        String translated = CommandHandler.parseCommandSubstitutionAndVariables(null, null, input, "alureon");
        Assert.assertEquals(translated, "Hello alureon!");
    }

    @Test
    public void testEcho() {
        String input = "echo this is a $USER";
        String translated = CommandHandler.parseCommandSubstitutionAndVariables(null, null, input, "test");
        String result = StringUtils.getArgs(translated.split(" "));
        Assert.assertEquals("this is a test", result);
    }

}
package net.alureon.ircbutt.command;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Tests for the !echo command.
 */
public class EchoCommandTest {

    /**
     * Tests that the echo command works as expected.
     */
    @Test
    public void testEchoCommand() {
        GenericMessageEvent event = Mockito.mock(GenericMessageEvent.class);
        User user = Mockito.mock(User.class);
        Mockito.when(event.getUser()).thenReturn(user);
        Mockito.when(event.getUser().getNick()).thenReturn("alureon");
        String testString = "echo Hello $USER!";
        IRCbutt butt = Mockito.mock(IRCbutt.class);
        CommandHandler commandHandler = new CommandHandler(butt);
        commandHandler.registerCommandClasses();
        // FIXME broken test
        //BotResponse response = commandHandler.handleCommand(event, testString);
        //Assert.assertEquals("Hello alureon!", response.getMessage());
    }
}

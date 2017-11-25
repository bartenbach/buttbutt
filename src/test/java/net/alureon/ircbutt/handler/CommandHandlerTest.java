package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.command.CommandHandler;
import net.alureon.ircbutt.response.BotResponse;
import org.junit.Assert;
import org.junit.Test;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;
import static org.mockito.Mockito.*;

public class CommandHandlerTest {

    @Test
    public void testEchoCommand() {
        GenericMessageEvent event = mock(GenericMessageEvent.class);
        User user = mock(User.class);
        when(event.getUser()).thenReturn(user);
        when(event.getUser().getNick()).thenReturn("test");
        String input = "echo this is a $USER";
        BotResponse response = new CommandHandler(null).handleCommand(event, input);
        Assert.assertEquals("this is a test", response.getMessage());
    }

    @Test
    public void testReflection() {
        CommandHandler.getCommandClasses();
    }
}
package net.alureon.ircbutt.command;

import net.alureon.ircbutt.IRCbuttTest;
import net.alureon.ircbutt.command.commands.Rot13Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;

/**
 * Tests for the Rot13 command.
 */
public final class Rot13CommandTest {

    /**
     * Tests the rot13 command.
     */
    public void testRot13() {
        String command = "!rot green";
        String command2 = "!rot 12345";
        String command3 = "!rot GREEN";
        GenericMessageEvent event = Mockito.mock(GenericMessageEvent.class);
        User user = Mockito.mock(User.class);
        Mockito.when(event.getUser()).thenReturn(user);
        Mockito.when(event.getUser().getNick()).thenReturn("alureon");
        // normal test
        BotResponse response = IRCbuttTest.getCommandHandler().handleCommand(event, command);
        Assert.assertEquals(null, response.getRecipient());
        Assert.assertEquals(null, response.getAdditionalMessage());
        Assert.assertEquals(BotIntention.CHAT, response.getIntention());
        Assert.assertEquals("terra", response.getMessage());
        // numbers test
        BotResponse response2 = IRCbuttTest.getCommandHandler().handleCommand(event, command2);
        Assert.assertEquals(null, response2.getRecipient());
        Assert.assertEquals(null, response2.getAdditionalMessage());
        Assert.assertEquals(BotIntention.CHAT, response2.getIntention());
        Assert.assertEquals("12345", response2.getMessage());
    }

    /**
     * Tests that the command aliases are set correctly for the rot13 command.
     */
    @Test
    public void testRot13CommandAliases() {
        ArrayList<String> commandAliases = new Rot13Command().getCommandAliases();
        Assert.assertEquals("rot", commandAliases.get(0));
        Assert.assertEquals("rot13", commandAliases.get(1));
    }

}


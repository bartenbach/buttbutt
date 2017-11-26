package net.alureon.ircbutt.command;

import net.alureon.ircbutt.command.commands.Rot13Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests for the Rot13 command.
 */
public final class Rot13CommandTest {

    /**
     * Tests the rot13 command.
     */
    @Test
    public void testRot13() {
        String[] command = {"rot", "green"};
        BotResponse response = new Rot13Command().executeCommand(null, null, command);
        Assert.assertEquals(null, response.getRecipient());
        Assert.assertEquals(null, response.getAdditionalMessage());
        Assert.assertEquals(BotIntention.CHAT, response.getIntention());
        Assert.assertEquals("terra", response.getMessage());
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


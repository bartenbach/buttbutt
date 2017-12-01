package net.alureon.ircbutt.command;

import net.alureon.ircbutt.IRCbuttTest;
import net.alureon.ircbutt.response.BotResponse;
import org.junit.Test;

/**
 * Tests the !uptime command.
 */
public final class UptimeCommandTest {

    /**
     *
     */
    @Test
    public void testUptime() {
        BotResponse response = IRCbuttTest.getCommandHandler().handleCommand(null, "!uptime");
        System.out.println(response.getMessage());
    }
}

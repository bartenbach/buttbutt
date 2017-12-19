package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides a quick way to check the memory usage of the bot in real time.
 */
public final class MemCommand implements Command {

    /**
     * A kibibyte is a multiple of the unit 'byte' for quantities of digital information.
     */
    private static final double KIBIBYTE = 1024;

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        double memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double megabytes = memory / KIBIBYTE / KIBIBYTE;
        String responseString = df.format(megabytes) + " MB";
        return new BotResponse(BotIntention.CHAT, null, responseString);
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("mem"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

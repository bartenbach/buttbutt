package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.MathUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides a way to generate a random number.
 */
public final class RandomCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        int random = MathUtils.getRandom(Integer.MIN_VALUE, Integer.MAX_VALUE - 1);
        return new BotResponse(BotIntention.CHAT, null, String.valueOf(random));
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("random"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

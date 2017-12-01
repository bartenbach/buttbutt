package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides a way to 'buttify' a String on demand.
 */
public final class ButtifyCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        String message = StringUtils.getArgs(cmd);
        String butted = butt.getButtReplaceHandler().buttifyMessage(message);
        if (message.equals(butted)) {
            return new BotResponse(BotIntention.CHAT, null, "couldn't butt");
        } else {
            return new BotResponse(BotIntention.CHAT, null, butted);
        }
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("butt", "buttify"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

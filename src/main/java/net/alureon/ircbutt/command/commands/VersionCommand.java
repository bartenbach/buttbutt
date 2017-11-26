package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides a way to get the current version of the bot.
 */
public final class VersionCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        return new BotResponse(BotIntention.CHAT, null, butt.getProgramVersion());
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("version"));
    }
}

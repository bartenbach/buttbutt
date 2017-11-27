package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.IRCUtils;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Command to !give a user something.
 */
public final class GiveCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
         if (event instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) event;
            if (cmd.length > 2 && IRCUtils.userIsInChannel(messageEvent, cmd[1])) {
                    return new BotResponse(BotIntention.CHAT, null, cmd[1] + ": "
                            + StringUtils.getArgsOverOne(cmd));
            }
        }
        return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!give <user> <text|command>");
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("give"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

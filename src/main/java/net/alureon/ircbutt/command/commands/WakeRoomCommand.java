package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This command wakes an IRC channel by pinging every user in the room.
 */
public final class WakeRoomCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        if (event instanceof MessageEvent) {
            MessageEvent channelEvent = (MessageEvent) event;
            Channel channel = channelEvent.getChannel();
            if (channel != null) {
                StringBuilder sb = new StringBuilder();
                for (User user : channel.getUsers()) {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }
                    sb.append(user.getNick());
                }
                return new BotResponse(BotIntention.CHAT, null, sb.toString());
            }
        }
        return new BotResponse(BotIntention.PRIVATE_MESSAGE, event.getUser(),
                "there aint nobody to wake up here!");
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("wr"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

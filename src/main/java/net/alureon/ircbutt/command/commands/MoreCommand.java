package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class handles the !more command, which can return extra results from commands that load results into it.
 * For instance, if a user Google searches something, there may be more than one result.  How does a user access
 * the other results?  This class solves that problem.
 */
public final class MoreCommand implements Command {


    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        if (!butt.getCommandHandler().getMoreList().isEmpty()) {
            String moreItem = butt.getCommandHandler().getMoreList().get(0);
            butt.getCommandHandler().getMoreList().remove(0);
            if (butt.getCommandHandler().getMoreList().size() == 0) {
                return new BotResponse(BotIntention.CHAT, null, moreItem);
            } else {
                return new BotResponse(BotIntention.CHAT, null, moreItem
                        + " [+" + butt.getCommandHandler().getMoreList().size() + " more]");
            }
        } else {
            return new BotResponse(BotIntention.CHAT, null, butt.getYamlConfigurationFile().getBotNickName()
                    + " don't have any more");
        }
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("more"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }

}

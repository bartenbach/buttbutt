package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Provides a way to invite the bot to different channels.
 * //TODO this could be abused....easily.
 */
public final class InviteCommand implements Command {

    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger();

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
         for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            for (String y : cmd) {
                log.debug("Attempting to join channel: " + y);
                if (x.equalsIgnoreCase(y)) {
                    butt.getPircBotX().sendIRC().joinChannel(x);
                }
            }
        }
        return new BotResponse(BotIntention.NO_REPLY, null, null);
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return (ArrayList<String>) Collections.singletonList("invite");
    }
}

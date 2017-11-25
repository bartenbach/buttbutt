package net.alureon.ircbutt.command.commands.karma;

import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Provides Karma functionality to the bot, where users can check an item's karma, decrease it, or increase it.
 */
public final class KarmaCommand implements Command {


    /**
     * The line endings that will trigger karma actions.
     */
    private static final String[] KARMA_ENDINGS = {"--;", "++;", "++", "--"};
    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();


    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        String message = StringUtils.getArgs(cmd);
        Integer karma = butt.getKarmaTable().getKarmaLevel(message);
        if (karma != null) {
            return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), message + " has a karma level of " + karma);
        } else {
            return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), message + " has no karma level yet");
        }
    }

    /**
     * Get the type of Karma operation we're doing.
     * @param x The Karma operation String
     * @return the KarmaType value matching the passed in String, or null if none exists.
     */
    private static KarmaType getKarmaType(final String x) {
        switch (x) {
            case "++":
            case "++;":
                return KarmaType.INCREMENT;
            case "--":
            case "--;":
                return KarmaType.DECREMENT;
            default:
                log.error("Unhandled Karma Type: " + x);
                return null;
        }
    }


    /**
     * TODO this functionality is not a command!  It is a listener.
     * @param butt The IRCbutt instance used for accessing the database.
     * @param cmd The String we are checking for a Karma operation.
     */
    public void karmaListener(final IRCbutt butt, final String[] cmd) {
        String message = StringUtils.arrayToString(cmd);
        if (message.contains(" ")) {
             return;
        }
        for (String x : KARMA_ENDINGS) {
            if (message.endsWith(x)) {
                Karma karma = new Karma();
                karma.setType(getKarmaType(x));
                karma.setItem(message.replace(x, "").trim());
                switch (karma.getType()) {
                    case DECREMENT:
                        butt.getKarmaTable().decrementKarma(karma);
                        break;
                    case INCREMENT:
                        butt.getKarmaTable().incrementKarma(karma);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("karma"));
    }
}

package net.alureon.ircbutt.command.commands.game;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.game.RegexGame;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A game that gives the chat a string to match with regex, and a string it CANNOT match!
 * The player who writes the correct regex string gets a point.
 */
public final class RegexGameCommand implements Command {
    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        if (cmd[0].equals("regexgame")) {
            return startNewRegexGame(butt);
        }
        return null;
    }

    /**
     * Starts a new regex game.
     * @param butt The IRCbutt instance for accessing the database
     * @return the bot's response
     */
    private BotResponse startNewRegexGame(final IRCbutt butt) {
        String match = butt.getFactTable().getRandomFactName();
        String cantMatch = butt.getFactTable().getRandomFactName();
        RegexGame regexGame = new RegexGame(match, cantMatch);
        butt.getGameManager().setActiveGame(regexGame);
        return new BotResponse(BotIntention.CHAT, null, "Regex Game started!",
                "Match: '" + match + "' Do not match: '" + cantMatch + "'");
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("regexgame"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

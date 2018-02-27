package net.alureon.ircbutt.command.commands.game;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.game.GuessingGame;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class provides a way for a user to start a new guessing game.
 */
public final class GuessingGameCommand implements Command {
    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        if (butt.getGameManager().getGameActive()) {
            if (cmd[0].equals("game")) {
                return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "a game is already active!");
            } else if (cmd[0].equals("endgame")) {
                // print game results
                StringBuilder sb = new StringBuilder();
                sb.append("Game Ended!  Final Scores: ");
                for (String x : butt.getGameManager().getActiveGame().getPlayers()) {
                    sb.append(x)
                            .append(": ")
                            .append(butt.getGameManager().getActiveGame().getScoreboard().get(x))
                            .append(" | ");
                }
                butt.getGameManager().setGameActive(false);
                butt.getGameManager().setActiveGame(null);
                return new BotResponse(BotIntention.CHAT, null, sb.toString());
            }
        } else {
            if (cmd.length > 1) {
                System.out.println("Starting new Guessing game with players: "
                        + StringUtils.arrayToString(StringUtils.getArgsArray(cmd)));
                GuessingGame guessingGame = new GuessingGame(butt, StringUtils.getArgsArray(cmd));
                butt.getGameManager().setActiveGame(guessingGame);
                butt.getGameManager().setGameActive(true);
                String fact = butt.getFactTable().getRandomFactName();
                guessingGame.setCurrentMysteryFactName(fact);
                return new BotResponse(BotIntention.CHAT, null,
                        "Guessing Game Started!  The first mystery fact is...",
                        butt.getFactTable().queryKnowledge(fact));
            } else {
                return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!game <player1> <player2>");
            }
        }
        return new BotResponse(BotIntention.NO_REPLY, null, null);
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("game", "endgame"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

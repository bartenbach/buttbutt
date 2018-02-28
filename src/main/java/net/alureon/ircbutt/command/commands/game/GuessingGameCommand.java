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
        if (butt.getGameManager().getGameActive() && butt.getGameManager().getActiveGame() instanceof GuessingGame) {
            GuessingGame game = (GuessingGame) butt.getGameManager().getActiveGame();
            if (cmd[0].equals("game")) {
                return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "a game is already active!",
                        "Current hint is: " + game.getCurrentHint());
            } else if (cmd[0].equals("endgame")) {
                String scores = game.getScores();
                butt.getGameManager().setGameActive(false);
                return new BotResponse(BotIntention.CHAT, null, "Game ended!", scores);
            } else if (cmd[0].equals("stumped")) {
                return game.addStumpedPlayer(event.getUser().getNick());
            }
        } else {
            if (cmd.length > 1) {
                System.out.println("Starting new Guessing game with players: "
                        + StringUtils.arrayToString(StringUtils.getArgsArray(cmd)) + " "
                        + butt.getYamlConfigurationFile().getBotNickName());
                GuessingGame guessingGame = new GuessingGame(butt, StringUtils.getArgsArray(cmd));
                butt.getGameManager().setActiveGame(guessingGame);
                String fact = butt.getFactTable().getRandomFactName();
                String hint = butt.getFactTable().queryKnowledge(fact);
                guessingGame.setCurrentMysteryFactName(fact);
                guessingGame.setCurrentHint(hint);
                return new BotResponse(BotIntention.CHAT, null,
                        "Guessing Game Started!  The first mystery fact is...", hint);
            } else {
                return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!game <player1> <player2>");
            }
        }
        return new BotResponse(BotIntention.NO_REPLY, null, null);
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("game", "endgame", "stumped"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

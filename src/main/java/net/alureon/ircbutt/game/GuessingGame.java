package net.alureon.ircbutt.game;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;

import java.util.HashMap;

/**
 * Provides the score keeping, players, and facts of a GuessingGame.
 */
public final class GuessingGame implements Game {

    /**
     * Needed for access to the fact table.
     */
    private IRCbutt butt;
    /**
     * The players in this guessing game.
     */
    private String[] players;
    /**
     * The scoreboard for this game.
     */
    private HashMap<String, Integer> scoreboard;
    /**
     * The current name of the fact users will guess.
     */
    private String currentMysteryFactName;

    /**
     * Creates a new Guessing Game.
     * @param butt the ircbutt instance needed for sql table access.
     * @param players the players in this game.
     */
    public GuessingGame(final IRCbutt butt, final String[] players) {
        this.butt = butt;
        this.players = players;
        this.scoreboard = new HashMap<>();
        for (String x : players) { // all players start with zero points
            this.scoreboard.put(x, 0);
        }
    }

    @Override
    public String[] getPlayers() {
        return this.players;
    }

    /**
     * Set the current mystery fact name.
     * @param mysteryFactName The fact name to guess.
     */
    public void setCurrentMysteryFactName(final String mysteryFactName) {
        this.currentMysteryFactName = mysteryFactName;
    }

    /**
     * Return the current mystery fact name.
     * @return the current name players are guessing.
     */
    public String getCurrentMysteryFactName() {
        return this.currentMysteryFactName;
    }

    /**
     * Increments a players score.
     * @param player the player to give one point to.
     * @return The bot's response to a correct guess.
     */
    public BotResponse givePlayerPoint(final String player) {
        this.scoreboard.put(player, this.scoreboard.get(player) + 1);
        String newItem = butt.getFactTable().getRandomFactName();
        this.currentMysteryFactName = newItem;
        return new BotResponse(BotIntention.CHAT, null, player + " got it!  The next fact is...",
                butt.getFactTable().queryKnowledge(newItem));
    }

    /**
     * Retrieves the scoreboard for this game.
     * @return (hashmap) the scoreboard.
     */
    public HashMap<String, Integer> getScoreboard() {
        return this.scoreboard;
    }
}

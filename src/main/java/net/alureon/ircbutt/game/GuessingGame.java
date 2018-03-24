package net.alureon.ircbutt.game;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;

import java.util.ArrayList;
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
     * Players who have given up on the current round.
     */
    private ArrayList<String> stumpedPlayers;
    /**
     * The scoreboard for this game.
     */
    private HashMap<String, Integer> scoreboard;
    /**
     * The current name of the fact users will guess.
     */
    private String currentMysteryFactName;
    /**
     * The current hint.
     */
    private String currentHint;
    /**
     * The number of points required to win the game.
     */
    private static final int WINNING_SCORE = 10;

    /**
     * Creates a new Guessing Game.
     *
     * @param butt the ircbutt instance needed for sql table access.
     */
    public GuessingGame(final IRCbutt butt) {
        this.butt = butt;
        this.scoreboard = new HashMap<>();
        this.stumpedPlayers = new ArrayList<>();
        // if everyone gives up, the bot gets a point
        this.scoreboard.put(butt.getYamlConfigurationFile().getBotNickName(), 0);
    }

    /**
     * Returns the stumped players array.
     *
     * @return the stumped players array
     */
    private ArrayList<String> getStumpedPlayers() {
        return this.stumpedPlayers;
    }

    /**
     * Set the current mystery fact name.
     *
     * @param mysteryFactName The fact name to guess.
     */
    public void setCurrentMysteryFactName(final String mysteryFactName) {
        this.currentMysteryFactName = mysteryFactName;
    }

    /**
     * Get the current hint.
     *
     * @return String - the current hint
     */
    public String getCurrentHint() {
        return this.currentHint;
    }

    /**
     * Set the current hint.
     *
     * @param hint The hint
     */
    public void setCurrentHint(final String hint) {
        this.currentHint = hint;
    }

    /**
     * Return the current mystery fact name.
     *
     * @return the current name players are guessing.
     */
    public String getCurrentMysteryFactName() {
        return this.currentMysteryFactName;
    }

    /**
     * Increments a players score.
     *
     * @param player the player to give one point to.
     * @return The bot's response to a correct guess.
     */
    public BotResponse givePlayerPoint(final String player) {
        // if the player hasn't answered a question yet, just add them to the game
        if (!this.scoreboard.containsKey(player)) {
            scoreboard.put(player, 0);
        }
        this.scoreboard.put(player, this.scoreboard.get(player) + 1);
        if (scoreboard.get(player) >= WINNING_SCORE) {
            BotResponse response = announceVictory(player);
            this.butt.getGameManager().setGameActive(false);
            return response;
        }
        return startNewRound(player);
    }

    /**
     * Announces the winner of the latest game.
     *
     * @param player The player who scored the winning point.
     * @return the bot's response
     */
    private BotResponse announceVictory(final String player) {
        return new BotResponse(BotIntention.CHAT, null, player + " got it!  WE HAVE A WINNER!!!",
                getScores());
    }

    /**
     * Gets a string that contains all player scores from the current game in the scoreboard.
     *
     * @return A string containing all player scores
     */
    public String getScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("Final Scores: ");
        // append all players
        for (String x : this.scoreboard.keySet()) {
            sb.append(x)
                    .append(": ")
                    .append(this.scoreboard.get(x))
                    .append(" | ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    /**
     * Starts a new round of the guessing game.
     *
     * @param player The player who guessed the previous fact name.
     * @return The Bot's Response
     */
    private BotResponse startNewRound(final String player) {
        String newItem = butt.getFactTable().getRandomFactName();
        String newHint = butt.getFactTable().queryKnowledge(newItem);
        this.getStumpedPlayers().clear();
        this.currentMysteryFactName = newItem;
        this.currentHint = newHint;
        return new BotResponse(BotIntention.CHAT, null, player + " got it!  The next fact is...",
                newHint);
    }

    /**
     * Retrieves the scoreboard for this game.
     *
     * @return (hashmap) the scoreboard.
     */
    public HashMap<String, Integer> getScoreboard() {
        return this.scoreboard;
    }

    /**
     * Adds a player to the stumped players list.
     *
     * @param nick The nick to add to the stumped players list.
     * @return the bot's response
     */
    public BotResponse addStumpedPlayer(final String nick) {
        this.stumpedPlayers.add(nick);
        boolean allStumped = checkAllPlayersStumped();
        if (allStumped) {
            String oldFact = this.currentMysteryFactName;
            givePlayerPoint(butt.getYamlConfigurationFile().getBotNickName());
            String newItem = butt.getFactTable().getRandomFactName();
            String newHint = butt.getFactTable().queryKnowledge(newItem);
            this.currentMysteryFactName = newItem;
            this.currentHint = newHint;
            this.getStumpedPlayers().clear();
            return new BotResponse(BotIntention.CHAT, null,
                    "All players are stumped!  The fact was: " + oldFact
                            + ".  The next fact is...", newHint);
        } else {
            return new BotResponse(BotIntention.CHAT, null, nick + " is stumped on this one!",
                    this.scoreboard.size() - this.getStumpedPlayers().size() + " players remain");
        }
    }

    /**
     * Checks to see if all players have been stumped.
     *
     * @return true if all players have given up
     */
    private boolean checkAllPlayersStumped() {
        return this.stumpedPlayers.size() >= this.scoreboard.size() - 1;
    }
}

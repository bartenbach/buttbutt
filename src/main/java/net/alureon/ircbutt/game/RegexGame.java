package net.alureon.ircbutt.game;

import java.util.HashMap;

/**
 * Allows players to play a regex golf game.
 */
public final class RegexGame implements Game {

    /**
     * This is the string that the regex should match.
     */
    private String shouldMatch;
    /**
     * This is the string that the regex CANNOT match.
     */
    private String shouldNotMatch;

    /**
     * The constructor for a new RegexGame.
     * @param shouldMatch The string that should match.
     * @param shouldNotMatch The string that should not match.
     */
    public RegexGame(final String shouldMatch, final String shouldNotMatch) {
        this.shouldMatch = shouldMatch;
        this.shouldNotMatch = shouldNotMatch;
    }

    @Override
    public HashMap<String, Integer> getScoreboard() {
        return null;
    }

    /**
     * Returns the string that should match.
     * @return the string that should match.
     */
    public String getShouldMatch() {
        return shouldMatch;
    }

    /**
     * Returns the string that should not match.
     * @return the string that should not match.
     */
    public String getShouldNotMatch() {
        return shouldNotMatch;
    }
}

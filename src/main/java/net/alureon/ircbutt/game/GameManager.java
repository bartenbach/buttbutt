package net.alureon.ircbutt.game;

/**
 * Provides a way to check the status of any games currently in progress.
 */
public final class GameManager {

    /**
     * Stores whether or not a game is currently active.
     */
    private boolean gameActive = false;
    /**
     * The currently active game.
     */
    private Game game;

    /**
     * Provides a way to set the game as active or not.
     * @param bool true if a game is active, otherwise false.
     */
    public void setGameActive(final boolean bool) {
        this.gameActive = bool;
    }

    /**
     * Get the currently active game (if one is active).
     * @return The currently active game (or null)
     */
    public Game getActiveGame() {
        return this.game;
    }

    /**
     * Set the currently active game to the provided game.
     * @param gameparam the game to set active
     */
    public void setActiveGame(final Game gameparam) {
        this.game = gameparam;
    }

    /**
     * Provides a way to check whether or not a game is currently active.
     * @return true if a game is active
     */
    public boolean getGameActive() {
        return this.gameActive;
    }
}

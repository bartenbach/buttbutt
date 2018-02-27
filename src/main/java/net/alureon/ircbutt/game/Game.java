package net.alureon.ircbutt.game;

import java.util.HashMap;

/**
 * Provides a unique interface for IRC games.
 */
public interface Game {
    /**
     * Get the players in the game.
     * @return the players.
     */
    String[] getPlayers();

    /**
     * Get the scoreboard of the game.
     * @return a hashmap containing every player's score
     */
    HashMap<String, Integer> getScoreboard();
}

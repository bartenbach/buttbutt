package net.alureon.ircbutt.util;

import java.util.Random;

/**
 * Provides a utility class for bot-related math functions.
 */
public final class MathUtils {

    /**
     * Prevent instantiation.
     */
    private MathUtils() {

    }

    /**
     * Returns whether or not the bot should randomly buttify a user's message in chat or not, based on a number
     * defined in the bot's configuration file.
     * @return True if it is time for the bot to randomly buttify a message in the IRC channel.
     */
    public static boolean isRandomResponseTime() {
        Random random = new Random();
        int test = random.nextInt(151);
        return test == 0;
    }

    /**
     * Returns a random number between min and max (inclusive).
     * @param min The minimum value this can return.
     * @param max The maximum value this can return.
     * @return The generated random number.
     */
    public static int getRandom(final int min, final int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}

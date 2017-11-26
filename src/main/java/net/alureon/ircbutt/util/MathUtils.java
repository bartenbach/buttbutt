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

package net.alureon.ircbutt.util;

import java.util.Random;

public class MathUtils {

    public static boolean isRandomResponseTime() {
        Random random = new Random();
        int test = random.nextInt(151);
        return test == 0;
    }

    //TODO hardcoded magic number
    public static int getRandom(final int highSide) {
        Random random = new Random();
        return random.nextInt(highSide);
    }

}

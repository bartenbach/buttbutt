package net.alureon.ircbutt.util;

import java.util.Random;

public class ButtMath {

    public static boolean isRandomResponseTime() {
        Random random = new Random();
        int test = random.nextInt(151);
        return test == 0;
    }

    public static int getRandom() {
        Random random = new Random();
        return random.nextInt(101);
    }

}

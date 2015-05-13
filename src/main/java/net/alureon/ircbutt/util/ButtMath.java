package net.alureon.ircbutt.util;

public class ButtMath {

    public static boolean isRandomResponseTime() {
        int test = (int) (Math.random()*100);
        return (test<=1);
    }

    public static int getRandom() {
        return (int) (Math.random() * 100);
    }

}

package net.alureon.ircbutt.libmath;

/**
 * Created by alureon on 1/28/15.
 */

public class Trigonometry {

    /* This is laughable at best */

    public static String getSin(String s) {
        return String.valueOf(Math.sin(getRadians(s)));
    }

    public static String getTan(String s) {
        return String.valueOf(Math.tan(getRadians(s)));
    }

    public static String getCos(String s) {
        return String.valueOf(Math.cos(getRadians(s)));
    }

    public static double getRadians(String input) {
        double x = Double.parseDouble(input);
        return Math.toRadians(x);
    }
}

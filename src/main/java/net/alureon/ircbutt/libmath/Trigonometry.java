package net.alureon.ircbutt.libmath;

import net.alureon.ircbutt.BotResponse;

import static java.lang.Math.*;
/**
 * Created by alureon on 1/28/15.
 */

public class Trigonometry {


    public static void handleTrigFunctions(BotResponse response, String[] cmd) {
        double x;
        if (cmd[1].equals("pi")) {
            x = PI;
        } else {
            x = Double.parseDouble(cmd[1]);
        }
        double f = 0;
        switch (cmd[0]) {
            case "sin":
                f = sin(x);
                break;
            case "cos":
                f = cos(x);
                break;
            case "tan":
                f = tan(x);
                break;
            case "arcsin":
                f = asin(x);
                break;
            case "arccos":
                f = acos(x);
                break;
            case "arctan":
                f = atan(x);
                break;
        }
        response.chat(String.valueOf(f));
    }
}

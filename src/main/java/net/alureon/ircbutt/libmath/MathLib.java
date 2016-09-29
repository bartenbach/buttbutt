package net.alureon.ircbutt.libmath;

import net.alureon.ircbutt.BotResponse;

import static java.lang.Math.*;

public class MathLib {

    public static void handleMath(BotResponse response, String[] cmd) {
        double x;
        if (cmd[1].equals("pi")) {
            x = PI;
        } else {
            x = Double.parseDouble(cmd[1]);
        }
        double f = 0;
        switch (cmd[0]) {
            case "sqrt":
                f = sqrt(x);
                break;
            case "pow":
                double y = Double.parseDouble(cmd[2]);
                f = pow(x, y);
                break;
        }
        response.chat(String.valueOf(f));
    }
}


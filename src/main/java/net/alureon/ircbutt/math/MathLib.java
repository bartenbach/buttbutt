package net.alureon.ircbutt.math;

import net.alureon.ircbutt.response.BotResponse;

import static java.lang.Math.*;

/**
 * MathLib houses static methods for dealing with arithmetic operations.
 */
public final class MathLib {

    /**
     * Prevent object instantiation.
     */
    private MathLib() {

    }

    /**
     * The handleMath method handles a few basic math functions.
     * It replaces the word "pi" with the actual value of pi, and
     * is able to do exponential multiplication, and perform square
     * root operations.
     * @param response - The BotResponse object
     * @param cmd - The command passed to the method.
     */
    public static void handleMath(final BotResponse response, final String[] cmd) {
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
            default:
                return;
        }
        response.chat(String.valueOf(f));
    }
}


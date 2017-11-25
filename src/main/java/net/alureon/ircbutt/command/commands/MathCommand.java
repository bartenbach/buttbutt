package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

/**
 * MathCommand houses static methods for dealing with arithmetic operations.
 */
public final class MathCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
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
                break;
        }
        return new BotResponse(BotIntention.CHAT, null, String.valueOf(f));
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return (ArrayList<String>) Arrays.asList("pow", "sqrt");
    }
}


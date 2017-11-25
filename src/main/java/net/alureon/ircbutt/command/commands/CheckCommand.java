package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.MathUtils;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Checks if something passes, fails, or panics based on random number generation.
 */
public final class CheckCommand implements Command {

    /**
     * The number to get a random from.  This will return 0 - this number.
     * If the result is anything above CHECK_MATH_FAIL, the result is PASS.
     */
    private static final int CHECK_MATH_MAX = 101;
    /**
     * If the random number is LESS than this number, the result is FAIL.
     */
    private static final int CHECK_MATH_FAIL = 52;
    /**
     * If the random number is LESS than this number, the result is PANIC.
     */
    private static final int CHECK_MATH_PANIC = 2;

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
         StringBuilder sb = new StringBuilder("Testing ");
        sb.append(StringUtils.getArgs(cmd)).append(": ");

        int random = MathUtils.getRandom(0, CHECK_MATH_MAX);
        String result;
        if (random < CHECK_MATH_PANIC) {
            result = Colors.WHITE + "[" + Colors.YELLOW + "PANIC" + Colors.WHITE + "]";
        } else if (random < CHECK_MATH_FAIL) {
            result = Colors.WHITE + "[" + Colors.RED + "FAIL" + Colors.WHITE + "]";
        } else {
            result = Colors.WHITE + "[" + Colors.GREEN + "PASS" + Colors.WHITE + "]";
        }
        sb.append(result);
        return new BotResponse(BotIntention.CHAT, event.getUser(), sb.toString());
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return (ArrayList<String>) Collections.singletonList("check");
    }
}

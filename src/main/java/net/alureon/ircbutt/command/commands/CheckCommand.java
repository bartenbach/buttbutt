package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.Colors;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Checks if something passes, fails, or rarely...panics.
 */
public final class CheckCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
         StringBuilder sb = new StringBuilder("Testing ");
        sb.append(checking).append(": ");

        double random = Math.random() * 100;
        String result;
        if (random < 2) {
            result = Colors.WHITE + "[" + Colors.YELLOW + "PANIC" + Colors.WHITE + "]";
        } else if (random < 52) {
            result = Colors.WHITE + "[" + Colors.RED + "FAIL" + Colors.WHITE + "]";
        } else {
            result = Colors.WHITE + "[" + Colors.GREEN + "PASS" + Colors.WHITE + "]";
        }
        sb.append(result);
        return new BotResponse(BotIntention.CHAT, event.getUser(), sb.toString());
    }
}

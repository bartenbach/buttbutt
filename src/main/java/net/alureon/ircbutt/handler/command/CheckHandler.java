package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import org.pircbotx.Colors;

public class CheckHandler {

    public static void handleCheck(BotResponse response, String checking) {
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
        response.chat(sb.toString());
    }

}

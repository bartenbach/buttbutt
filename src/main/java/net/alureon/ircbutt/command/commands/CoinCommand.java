package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.response.BotResponse;

public class CoinCommand {

    public static void handleCoin(BotResponse response) {
        double random = Math.random() * 100;
        String result;
        if (random < 2) {
            result = "penis";
        } else if (random < 52) {
            result = "heads";
        } else {
            result = "tails";
        }
        response.chat(result);
    }

}

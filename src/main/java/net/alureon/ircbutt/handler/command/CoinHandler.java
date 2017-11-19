package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;

public class CoinHandler {

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

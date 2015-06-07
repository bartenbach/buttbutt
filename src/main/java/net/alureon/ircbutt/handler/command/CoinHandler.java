package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;

/**
 * Created by alureon on 5/17/15.
 */
public class CoinHandler {

    public static void handleCoin(BotResponse response) {
        double random = Math.random()*100;
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

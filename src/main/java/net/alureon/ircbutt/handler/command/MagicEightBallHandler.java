package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;

import java.util.Random;

public class MagicEightBallHandler {

    public static void handleMagicEightBall(BotResponse response) {
        Random random = new Random();
        int rand = random.nextInt(20);
        response.chat(getMagic8BallResponse(rand));
    }

    private static String getMagic8BallResponse(int number) {
        switch (number) {
            case 0:
                return "It is certain";
            case 1:
                return "It is decidedly so";
            case 2:
                return "Without a doubt";
            case 3:
                return "Yes, definitely";
            case 4:
                return "You may rely on it";
            case 5:
                return "As I see it, yes";
            case 6:
                return "Outlook good";
            case 7:
                return "Yes";
            case 8:
                return "Signs point to yes";
            case 9:
                return "Reply hazy, try again";
            case 10:
                return "Ask again later";
            case 11:
                return "Better not tell you now";
            case 12:
                return "Cannot predict now";
            case 13:
                return "Concentrate and ask again";
            case 14:
                return "Don't count on it";
            case 15:
                return "My reply is no";
            case 16:
                return "My sources say no";
            case 17:
                return "Outlook not so good";
            case 18:
                return "Very doubtful";
            case 19:
                return "Most likely";
            default:
                return "this should never happen - invalid switch value";
        }
    }
}

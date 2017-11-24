package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.response.BotResponse;

import java.util.Random;

public class MagicEightBallCommand {

    public static void handleMagicEightBall(BotResponse response) {
        Random random = new Random();
        int rand = random.nextInt(20);
        response.chat(getMagic8BallResponse(rand));
    }

    private static String getMagic8BallResponse(int number) {
        switch (number) {
            case 0:
                return "(8) It is certain";
            case 1:
                return "(8) It is decidedly so";
            case 2:
                return "(8) Without a doubt";
            case 3:
                return "(8) Yes, definitely";
            case 4:
                return "(8) You may rely on it";
            case 5:
                return "(8) As I see it, yes";
            case 6:
                return "(8) Outlook good";
            case 7:
                return "(8) Yes";
            case 8:
                return "(8) Signs point to yes";
            case 9:
                return "(8) Reply hazy, try again";
            case 10:
                return "(8) Ask again later";
            case 11:
                return "(8) Better not tell you now";
            case 12:
                return "(8) Cannot predict now";
            case 13:
                return "(8) Concentrate and ask again";
            case 14:
                return "(8) Don't count on it";
            case 15:
                return "(8) My reply is no";
            case 16:
                return "(8) My sources say no";
            case 17:
                return "(8) Outlook not so good";
            case 18:
                return "(8) Very doubtful";
            case 19:
                return "(8) Most likely";
            default:
                return "this should never happen - invalid switch value";
        }
    }
}

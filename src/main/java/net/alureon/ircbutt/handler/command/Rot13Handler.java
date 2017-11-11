package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;

public class Rot13Handler {

    public static String handleRot13(BotResponse response, String in) {
        StringBuilder sb = new StringBuilder();
        for (char c : in.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char b = (char) (c + (byte) 13);
                if (b > 'Z') {
                    b -= 26;
                }
                sb.append(b);
            } else if (c >= 'a' && c <= 'z') {
                char b = (char) (c + (byte) 13);
                if (b > 'z') {
                    b -= 26;
                }
                sb.append(b);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

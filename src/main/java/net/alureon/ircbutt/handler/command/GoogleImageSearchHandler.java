package net.alureon.ircbutt.handler.command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import net.alureon.ircbutt.BotResponse;

public class GoogleImageSearchHandler {

    public static void handleGoogleImageSearch(BotResponse response, String args) {
        try {
            response.chat("https://google.com/search?tbm=isch&q=" + URLEncoder.encode(args, "utf8"));
        } catch (UnsupportedEncodingException ex) {
        }
    }
}
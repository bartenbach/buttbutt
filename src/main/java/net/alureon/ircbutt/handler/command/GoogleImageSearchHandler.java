package net.alureon.ircbutt.handler.command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import net.alureon.ircbutt.BotResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleImageSearchHandler {

    private final static Logger log = LoggerFactory.getLogger(GoogleImageSearchHandler.class);

    public static void handleGoogleImageSearch(BotResponse response, String args) {
        try {
            response.chat("https://google.com/search?tbm=isch&q=" + URLEncoder.encode(args, "utf8"));
        } catch (UnsupportedEncodingException ex) {
            log.info("Failed to get image search url: ", ex);
        }
    }
}
package net.alureon.ircbutt.command.commands.google;

import net.alureon.ircbutt.response.BotResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleImageSearchCommand {

    private final static Logger log = LogManager.getLogger(GoogleImageSearchCommand.class);

    public static void handleGoogleImageSearch(BotResponse response, String args) {
        try {
            response.chat("https://google.com/search?tbm=isch&q=" + URLEncoder.encode(args, "utf8"));
        } catch (UnsupportedEncodingException ex) {
            log.info("Failed to get image search url: ", ex);
        }
    }
}
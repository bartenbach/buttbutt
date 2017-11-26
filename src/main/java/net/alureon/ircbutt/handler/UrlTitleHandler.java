package net.alureon.ircbutt.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the functionality for chatting URL titles when a URL is posted in the channel.
 */
final class UrlTitleHandler {

    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * A regular expression for detecting URL's.
     */
    private static final String URL_REGEX = "((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?";

    /**
     * Prevent instantiation.
     */
    private UrlTitleHandler() {

    }

    /**
     * Handles URL's for the bot.  If the message was found to contain a URL, the title is retrieved and
     * sent to the chat.
     * @param channel The channel to send the URL title to.
     * @param message The message in chat that may or may not contain a URL.
     * @return True if the chat message contained a URL, false if not.
     */
    static boolean handleUrl(final Channel channel, final String message) {
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(message);
        if (m.find()) {
            String title = getTitle(m.group());
            if (title != null) {
                channel.send().message("Title: " + title);
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to get the title for a given URL.
     * @param url The URL to attempt to get a title for.
     * @return The title of the URL, or null if an error was encountered.
     */
    private static String getTitle(final String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements n = doc.select("title");
            return n.first().text();
        } catch (IOException e) {
            log.error("Failed to get title for URL: " + url);
            log.error("Exception thrown: " + e.getMessage());
        }
        return null;
    }

}

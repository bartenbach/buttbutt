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
     * The magic number that gets the YouTube title.
     */
    private static final int YOUTUBE_MAGIC_NUMBER = 8;

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
    public static String getTitle(final String url) {
        try {
            System.out.println(url);
            if (url.startsWith("https://youtube") || url.startsWith("http://youtube")
                    || url.startsWith("http://www.youtube") || url.startsWith("https://www.youtube")
                    || url.startsWith("http://www.youtu.be") || url.startsWith("https://www.youtu.be")
                    || url.startsWith("http://youtu.be") || url.startsWith("https://youtu.be")) {
                log.debug("Handling youtube url...");
                Document doc = Jsoup.connect(url).get();
                Elements script = doc.select("script");  //to get the script content
                Pattern p = Pattern.compile("\"title\":\"(.+?)\"");
                Matcher m = p.matcher(script.html());
                if (m.find()) {
                    return m.group().substring(YOUTUBE_MAGIC_NUMBER).replaceAll("\"", "");
                }
            } else {
                Document doc = Jsoup.connect(url).get();
                return doc.title();
            }
        } catch (IOException e) {
            log.warn("Failed to get title for URL: " + url + " this may be expected.\n  Reason: " + e.getMessage());
        }
        return null;
    }

}

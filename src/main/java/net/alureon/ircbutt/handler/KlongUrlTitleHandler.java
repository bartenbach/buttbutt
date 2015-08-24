package net.alureon.ircbutt.handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Created by Klong and alureon */

public class KlongUrlTitleHandler {


    public final static Logger log = LoggerFactory.getLogger(KlongUrlTitleHandler.class);
    public static final String URL_REGEX = "((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?";


    public static void handleUrl(Channel channel, String message) {
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(message);
        if (m.find()) {
            String title = getTitle(m.group());
            if (title != null) {
                channel.send().message("Title: " + title);
            }
        }
    }

    public static String getTitle(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements n = doc.select("title");
            return n.first().text();
        } catch (Exception ex) {
            // This can throw IOException, MalformedUrlException, IllegalArgumentException...maybe more
            // Pull requests accepted...
            return null;
        }
    }

}

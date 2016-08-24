package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by alureon on 8/24/2016.
 */

public class GoogleSearchHandler {

    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(GoogleSearchHandler.class);

    public GoogleSearchHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public static void handleGoogleSearch(IRCbutt butt, BotResponse response, User user, String[] cmd) {
        String google = "http://www.google.com/search?q=";
        String search = StringUtils.getArgs(cmd);
        String charset = "UTF-8";
        String userAgent = "IRCButt (+https://github.com/proxa/IRCbutt)";

        try {
            Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
            int size = links.size();
            for (int i = 0; i < size; i++) {
                String title = links.get(i).text();
                String url = links.get(i).absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }

                if (i==0) {
                    response.chat("Title: " + title, url);
                } else {
                    butt.getMoreHandler().addMore("Title: " + title + " " + url);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("UnsupportedEncodingException encountered!", ex);
            response.chat("butt error :(");
        } catch (IOException ex) {
            log.error("IOException encountered!", ex);
            response.chat("butt error :(");
        }
    }
}

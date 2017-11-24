package net.alureon.ircbutt.command.commands.google;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class GoogleSearchCommand {


    private final static Logger log = LogManager.getLogger(GoogleSearchCommand.class);


    public static void handleGoogleSearch(IRCbutt butt, BotResponse response, String[] cmd) {
        // clear !more list
        butt.getMoreCommand().clearMore();

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

                if (i == 0) {
                    response.chat("Title: " + title, url);
                } else {
                    butt.getMoreCommand().addMore("Title: " + title + " " + url);
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

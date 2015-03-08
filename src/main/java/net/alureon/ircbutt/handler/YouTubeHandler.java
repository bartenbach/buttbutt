package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by alureon on 3/8/15.
 */
public class YouTubeHandler {

    final static Logger log = LoggerFactory.getLogger(YouTubeHandler.class);

    public void getYouTubeVideo(BotResponse response, String link) {
        StringBuilder sb = new StringBuilder();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

        try {
            Document doc = Jsoup.connect(link).userAgent(userAgent).get();
            Element result = doc.getElementById("results");
            Elements videos = result.getElementsByClass("yt-lockup-title");
            Element firstVideo = videos.get(0);
            Attributes attributes = firstVideo.child(0).attributes();
            String url = attributes.get("href");
            sb.append(firstVideo.text() + " " + "http://youtube.com" + url);
            response.chat(sb.toString());
        } catch (IOException | NullPointerException ex) {
            log.error("Found no videos", ex);
            response.privateMessage(response.getRecipient(), "Found no videos");
        }
    }
}

package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by alureon on 3/8/15.
 */
public class YouTubeHandler {


    final static Logger log = LoggerFactory.getLogger(YouTubeHandler.class);

    //TODO this is so sloppy it's hard to look at
    public static void getYouTubeVideo(IRCbutt butt, BotResponse response, String[] cmd) {
        butt.getMoreHandler().clearMore();
        try {
            String link = "http://www.youtube.com/results?search_query=" + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Document doc = Jsoup.connect(link).userAgent(userAgent).get();
                Element result = doc.getElementById("results");
                Elements videos = result.getElementsByClass("yt-lockup-title");
                int size = videos.size();
                for (int i = 0; i < size; i++) {
                    Element video = videos.get(i);
                    Attributes attributes = video.child(0).attributes();
                    String url = attributes.get("href");
                    if (i == 0) {
                        response.chat(URLDecoder.decode(video.text(), "utf8") + " http://youtube.com" + url);
                    } else {
                        butt.getMoreHandler().addMore(URLDecoder.decode(video.text(), "utf-8") + " http://youtube.com" + url);
                    }
                }
                butt.getMoreHandler().setNoMore("how about you refine your search terms instead?");
            } catch (IOException | NullPointerException ex) {
                log.error("Found no video", ex);
                response.privateMessage(response.getRecipient(), "Found no video");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

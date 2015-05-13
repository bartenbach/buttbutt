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


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(YouTubeHandler.class);


    public YouTubeHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void getYouTubeVideo(BotResponse response, String[] cmd) {
        butt.getMoreHandler().clearMore();
        try {
            String link = "http://www.youtube.com/results?search_query=" + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Document doc = Jsoup.connect(link).userAgent(userAgent).get();
                Element result = doc.getElementById("results");
                Elements videos = result.getElementsByClass("yt-lockup-title");
                Element firstVideo = videos.get(0);
                Element secondVideo = videos.get(1);
                Element thirdVideo = videos.get(2);
                Attributes attributes = firstVideo.child(0).attributes();
                Attributes attributes2 = secondVideo.child(0).attributes();
                Attributes attributes3 = thirdVideo.child(0).attributes();
                String url = attributes.get("href");
                String url2 = attributes2.get("href");
                String url3 = attributes3.get("href");
                response.chat(URLDecoder.decode(firstVideo.text(), "utf8") + " http://youtube.com" + url);
                butt.getMoreHandler().setMore(URLDecoder.decode(secondVideo.text(), "utf-8") + " http://youtube.com" + url2);
                butt.getMoreHandler().setMore2(URLDecoder.decode(thirdVideo.text(), "utf-8") + " http://youtube.com" + url3);
                butt.getMoreHandler().setMore3("how about you refine your search terms instead?");
            } catch (IOException | NullPointerException ex) {
                log.error("Found no video", ex);
                response.privateMessage(response.getRecipient(), "Found no video");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

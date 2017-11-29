package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The command implements the functionality to search for YouTube videos from the IRC channel.
 */
public final class YouTubeCommand implements Command {

    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * The timeout for making the web request to YouTube.
     */
    private static final int REQUEST_TIMEOUT = 12000;

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        butt.getCommandHandler().clearMore();
         BotResponse response = new BotResponse(BotIntention.CHAT, null, "found no videos bout that");
        try {
            String link = "http://www.youtube.com/results?search_query="
                    + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Connection.Response cResponse = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .userAgent(userAgent)
                        .referrer("http://www.google.com")
                        .timeout(REQUEST_TIMEOUT)
                        .followRedirects(true)
                        .execute();
                Document doc = cResponse.parse();
                Element result = doc.getElementById("results");
                Elements videos = result.getElementsByClass("yt-lockup-title");
                int size = videos.size();
                for (int i = 0; i < size; i++) {
                    Element video = videos.get(i);
                    Attributes attributes = video.child(0).attributes();
                    String url = attributes.get("href");
                    if (i == 0) {
                        String urlText = URLDecoder.decode(video.text(), "utf8") + " http://youtube.com" + url;
                        response = new BotResponse(BotIntention.CHAT, null, urlText);
                    } else {
                        butt.getCommandHandler().addMore(URLDecoder.decode(video.text(), "utf-8")
                                + " http://youtube.com" + url);
                    }
                }
            } catch (IOException | NullPointerException ex) {
                log.error("Found no video", ex);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to query for YouTube videos ", e.getMessage());
        }
        return response;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("yt"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

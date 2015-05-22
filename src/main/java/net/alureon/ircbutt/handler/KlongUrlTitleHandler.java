package net.alureon.ircbutt.handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Created by Klong and alureon */

public class KlongUrlTitleHandler {

    public static void handleUrl(Channel channel, String text) {
        for (String link : links(text)) {
            try {
                String title = getTitle(link);
                if (title != null) {
                    channel.send().message("Title: " + title);
                }
            } catch (Exception e) {
                channel.send().message(e.getMessage());
            }
        }
    }

    public static String getTitle(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements n = doc.select("title");
        return n.first().text();
    }

    public static List<String> links(String text) {
        if (text.contains("http://") || text.contains("https://")) {
            ArrayList<String> links = new ArrayList<>();
            String[] parts = text.split(" ");
            for (String part : parts) {
                if (part.startsWith("http://") || part.startsWith("https://")) {
                    links.add(part);
                }
            }
            return links;
        }
        return Collections.EMPTY_LIST;
    }

}

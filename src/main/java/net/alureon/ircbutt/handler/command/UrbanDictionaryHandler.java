package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by alureon and Klong on 5/13/15.
 */

public class UrbanDictionaryHandler {


    private final static Logger log = LogManager.getLogger(UrbanDictionaryHandler.class);


    public static void getDefinition(IRCbutt butt, BotResponse response, String[] cmd) {
        butt.getMoreHandler().clearMore();
        try {
            String link = "http://www.urbandictionary.com/define.php?term=" + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Document doc = Jsoup.connect(link).userAgent(userAgent).get();
                Elements meanings = doc.getElementsByClass("meaning");
                try {
                    int size = meanings.size();
                    for (int i = 0; i < size; i++) {
                        Element meaning = meanings.get(i);
                        if (i == 0) {
                            response.chat(meaning.text());
                        } else {
                            butt.getMoreHandler().addMore(meaning.text());
                        }
                    }
                    butt.getMoreHandler().setNoMore(link);
                } catch (NullPointerException ex) {
                    log.info("No more definitions to get");
                }
            } catch (IOException ex) {
                log.error("Exception encountered", ex);
                response.privateMessage(response.getRecipient(), "Found no definition");
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("Failed to encode URL", ex);
        }
    }
}

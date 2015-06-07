package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by alureon and Klong on 5/13/15.
 *
 */

public class UrbanDictionaryHandler {


    final static Logger log = LoggerFactory.getLogger(UrbanDictionaryHandler.class);


    public static void getDefinition(IRCbutt butt, BotResponse response, String[] cmd) {
        butt.getMoreHandler().clearMore();
        try {
            String link = "http://www.urbandictionary.com/define.php?term=" + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Document doc = Jsoup.connect(link).userAgent(userAgent).get();
                Elements meaning = doc.getElementsByClass("meaning");
                Element firstDefinition = getElement(meaning, 0);
                Element secondDefinition = getElement(meaning, 1);
                Element thirdDefinition = getElement(meaning, 2);
                try {
                    // todo this is so lazy  need to fix this bad
                    response.chat(firstDefinition.text());
                    butt.getMoreHandler().setMore(secondDefinition.text());
                    butt.getMoreHandler().setMore2(thirdDefinition.text());
                    butt.getMoreHandler().setMore3(link);
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

    public static Element getElement(Elements elements, int index) {
        try {
            Element e =  elements.get(index);
            return e;
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }
}

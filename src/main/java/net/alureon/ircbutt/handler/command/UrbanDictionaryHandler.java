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
    private IRCbutt butt;


    public UrbanDictionaryHandler(IRCbutt butt) {
        this.butt = butt;
    }


    public void getDefinition(BotResponse response, String[] cmd) {
        butt.getMoreHandler().clearMore();
        try {
            String link = "http://www.urbandictionary.com/define.php?term=" + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Document doc = Jsoup.connect(link).userAgent(userAgent).get();
                Elements meaning = doc.getElementsByClass("meaning");
                Element firstDefinition = meaning.get(0);
                Element secondDefinition = meaning.get(1);
                Element thirdDefinition = meaning.get(2);
                response.chat(firstDefinition.text());
                butt.getMoreHandler().setMore(secondDefinition.text());
                butt.getMoreHandler().setMore2(thirdDefinition.text());
                butt.getMoreHandler().setMore3(link);
            } catch (IOException | NullPointerException ex) {
                log.error("Exception encountered", ex);
                response.privateMessage(response.getRecipient(), "Found no definition");
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("Failed to encode URL", ex);
        }
    }
}

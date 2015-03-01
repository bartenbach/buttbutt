package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by alureon on 3/1/15.
 */

public class DefineHandler {

    final static Logger log = LoggerFactory.getLogger(DefineHandler.class);

    public void handleDefine(BotResponse response, String word) {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

        try {
            Document doc = Jsoup.connect("http://www.merriam-webster.com/dictionary/"+word).userAgent(userAgent).get();
            Elements definition = doc.getElementsByClass("ld_on_collegiate");
            if (definition != null) {
                response.chat(definition.first().text());
            } else {
                response.chat("no definition found for " + word);
            }
        } catch (IOException ex) {
            log.error("We suck. ", ex);
            response.chat("couldn't get a definition for " + word);
        }
    }
}

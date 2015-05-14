package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
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


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(DefineHandler.class);


    public DefineHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleDefine(BotResponse response, String word) {
        butt.getMoreHandler().clearMore();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
        try {
            Document doc = Jsoup.connect("http://www.merriam-webster.com/dictionary/"+word).userAgent(userAgent).get();
            Elements definitions = doc.getElementsByClass("ssens");
            response.chat(definitions.get(0).text());
            butt.getMoreHandler().setMore(definitions.get(1).text());
            butt.getMoreHandler().setMore2(definitions.get(2).text());
            butt.getMoreHandler().setMore3("get your own dictionary pal");
        } catch (IOException ex) {
            log.error("DefineHandler IOException", ex);
            response.chat(ex.getMessage());
        } catch (NullPointerException ex) {
            log.error("DefineHandler NullPointerException", ex);
            response.chat(ex.getMessage());
        }
    }
}

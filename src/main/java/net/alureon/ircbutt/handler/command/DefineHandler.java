package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by alureon on 3/1/15.
 */

public class DefineHandler {


    final static Logger log = LoggerFactory.getLogger(DefineHandler.class);


    public static void handleDefine(IRCbutt butt, BotResponse response, String word) {
        butt.getMoreHandler().clearMore();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
        try {
            String link = "http://www.merriam-webster.com/dictionary/"+word;
            Document doc = Jsoup.connect(link).userAgent(userAgent).get();
            Elements definitions = doc.getElementsByClass("ssens");
            if (definitions.size() > 0) {
                response.chat(definitions.get(0).text().replaceAll("  ", " "));
                if (definitions.size() > 1) {
                    butt.getMoreHandler().setMore(definitions.get(1).text());
                    if (definitions.size() > 2) {
                        butt.getMoreHandler().setMore2(definitions.get(2).text());
                        if (definitions.size() > 3) {
                            butt.getMoreHandler().setMore3(link);
                        } else {
                            butt.getMoreHandler().setMore3("thats all there was");
                        }
                    } else {
                        butt.getMoreHandler().setMore2("that was all butt found");
                    }
                } else {
                    butt.getMoreHandler().setMore("butt dont see any other definition for that");
                }
            } else {
                response.chat("butt didnt find nothin for that");
            }
        } catch (IOException ex) {
            log.error("DefineHandler IOException " + ex.getMessage());
            response.chat("word not found in merriam webster");
        } catch (NullPointerException ex) {
            log.error("DefineHandler NullPointerException", ex);
            response.chat(ex.getMessage());
        }
    }
}

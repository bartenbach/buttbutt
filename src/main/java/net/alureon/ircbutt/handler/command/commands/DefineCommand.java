package net.alureon.ircbutt.handler.command.commands;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DefineCommand {


    private final static Logger log = LogManager.getLogger(DefineCommand.class);


    public static void handleDefine(IRCbutt butt, BotResponse response, String word) {
        butt.getMoreCommand().clearMore();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
        try {
            String link = "http://www.merriam-webster.com/dictionary/" + word;
            Document doc = Jsoup.connect(link).userAgent(userAgent).get();
            Elements definitions = doc.getElementsByClass("definition-inner-item");
            int size = definitions.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    Element definition = definitions.get(i);
                    String text = definition.text().replaceAll("  ", " ");
                    if (i == 0) {
                        response.chat(text);
                    } else {
                        butt.getMoreCommand().addMore(text);
                    }
                }
                butt.getMoreCommand().addMore(link);
            } else {
                response.chat("butt didnt find nothin for that");
            }
        } catch (IOException ex) {
            log.error("DefineCommand IOException " + ex.getMessage());
            response.chat("word not found in merriam webster");
        } catch (NullPointerException ex) {
            log.error("DefineCommand NullPointerException", ex);
            response.chat(ex.getMessage());
        }
    }
}

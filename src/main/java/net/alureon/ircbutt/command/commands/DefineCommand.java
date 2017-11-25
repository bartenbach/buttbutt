package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class retrieves definitions of words from MerriamWebster.com.
 */
public final class DefineCommand implements Command {


    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger(DefineCommand.class);

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        butt.getMoreCommand().clearMore();
        BotResponse response = new BotResponse(BotIntention.CHAT, null, "this should never happen");
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
        try {
            String link = "http://www.merriam-webster.com/dictionary/" + cmd[1];
            Document doc = Jsoup.connect(link).userAgent(userAgent).get();
            Elements definitions = doc.getElementsByClass("definition-inner-item");
            int size = definitions.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    Element definition = definitions.get(i);
                    String text = definition.text().replaceAll("\\s\\s", " ");
                    if (i == 0) {
                        response = new BotResponse(BotIntention.CHAT, null, text);
                    } else {
                        butt.getMoreCommand().addMore(text);
                    }
                }
                butt.getMoreCommand().addMore(link);
            } else {
                response = new BotResponse(BotIntention.CHAT, null, "butt didnt find nothin for that");
            }
        } catch (IOException | NullPointerException ex) {
            log.error("DefineCommand Exception: " + ex.getMessage());
        }
        return response;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("define"));
    }
}

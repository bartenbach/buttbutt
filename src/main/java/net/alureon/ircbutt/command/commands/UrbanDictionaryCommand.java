package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  This command retrieves word definitions from UrbanDictionary.
 */
public final class UrbanDictionaryCommand implements Command {

    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        butt.getCommandHandler().clearMore();
        BotResponse response = new BotResponse(BotIntention.CHAT, null, "this should never happen");
        try {
            String link = "http://www.urbandictionary.com/define.php?term="
                    + URLEncoder.encode(StringUtils.getArgs(cmd), "utf-8");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
            try {
                Document doc = Jsoup.connect(link).userAgent(userAgent).get();
                Elements meanings = doc.getElementsByClass("meaning");
                try {
                    int size = meanings.size();
                    for (int i = 0; i < size; i++) {
                        Element meaning = meanings.get(i);
                        if (i == 0) {
                            response = new BotResponse(BotIntention.CHAT, null, meaning.text());
                        } else {
                            butt.getCommandHandler().addMore(meaning.text());
                        }
                    }
                    butt.getCommandHandler().addMore(link);
                } catch (NullPointerException ex) {
                    log.info("No more definitions to get");
                }
            } catch (IOException ex) {
                log.error("Exception encountered", ex);
                response = new BotResponse(BotIntention.CHAT, null, "butt don't see that word nowhere");
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("Failed to encode URL", ex);
        }
        return response;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("ud"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

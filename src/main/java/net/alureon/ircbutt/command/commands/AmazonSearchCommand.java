package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Provides functionality for doing Amazon searches in an IRC channel.
 */
public final class AmazonSearchCommand implements Command {

    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger();


    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        butt.getCommandHandler().clearMore();
        BotResponse response = new BotResponse(BotIntention.CHAT, null, "found nothing bout that");

        try {
            String amazon = "http://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
            String search = StringUtils.getArgs(cmd);
            Connection.Response cResponse = Jsoup.connect(amazon + URLEncoder.encode(search, "UTF-8"))
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) "
                            + "Chrome/58.0.3029.110 Safari/537.36")
                    .referrer("http://www.google.com")
                    .followRedirects(true)
                    .execute();
            Document doc = cResponse.parse();
            Elements items = doc.getElementsByClass("s-result-item");
            Elements ids = doc.getElementsByAttribute("data-asin");
            int results = items.size();
            for (int i = 0; i < items.size(); i++) {
                Elements title = items.get(i).getElementsByClass("s-access-title");
                if (title.size() > 0) {
                    if (i == 0) {
                        response = new BotResponse(BotIntention.CHAT, null, title.get(0).text()
                                + " [" + results + " more]",
                                "http://amazon.com/dp/" + ids.get(i).attr("data-asin"));
                    } else {
                        butt.getCommandHandler().addMore(title.get(0).text() + " http://amazon.com/dp/"
                                + ids.get(i).attr("data-asin"));
                    }
                }
            }
        } catch (IOException e) {
            log.error("Found no result");
        }
        return response;
    }


    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("a"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

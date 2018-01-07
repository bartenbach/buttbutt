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
import java.util.Arrays;


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
        BotResponse response = new BotResponse(BotIntention.CHAT, null,
                butt.getYamlConfigurationFile().getBotNickName() + " found nothing bout that");

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
            int results = items.size();
            boolean first = true;
            for (int i = 0; i < items.size(); i++) {
                try {
                    Elements ids = items.get(i).getElementsByAttribute("data-asin");
                    if (ids.size() == 0) {
                        continue;
                    }
                    String url = "http://amazon.com/dp/" + ids.get(0).attr("data-asin");
                    Elements title = items.get(i).getElementsByClass("s-access-title");
                    String titleString = title.get(0).text();
                    //log.info(title + " " + url);
                    if (title.size() > 0) {
                        if (first) {
                            response = new BotResponse(BotIntention.CHAT, null, titleString.replace("[Sponsored]", "")
                                    + " [+" + results + " more]", url);
                            first = false;
                        } else {
                            butt.getCommandHandler().addMore(titleString.replace("[Sponsored]", "") + " " + url);
                        }
                    } else {
                        return new BotResponse(BotIntention.CHAT, null,
                                butt.getYamlConfigurationFile().getBotNickName() + " didn't find nothin");
                    }
                } catch (IndexOutOfBoundsException ex) {
                    log.warn("Index was out of bounds in AmazonSearchCommand.  Skipping...");
                }
            }
        } catch (IOException e) {
            log.error("Found no result");
        }
        return response;
    }


    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("a", "amazon"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

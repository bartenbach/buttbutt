package net.alureon.ircbutt.command.commands.google;

import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides functionality for doing Google searches in an IRC channel.
 */
public final class GoogleSearchCommand implements Command {


    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger(GoogleSearchCommand.class);

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        // clear !more list
        butt.getCommandHandler().clearMore();

        String google = "http://www.google.com/search?q=";
        String search = StringUtils.getArgs(cmd);
        String charset = "UTF-8";
        String userAgent = "IRCButt (+https://github.com/proxa/IRCbutt)";
        String response = "";
        String additionalResponse = "";
        try {
            Elements links = Jsoup.connect(google
                    + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
            int size = links.size();
            for (int i = 0; i < size; i++) {
                String title = links.get(i).text();
                String url = links.get(i).absUrl("href");
                // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }

                if (i == 0) {
                    response = "Title: " + title;
                    additionalResponse = url;
                } else {
                    butt.getCommandHandler().addMore("Title: " + title + " " + url);
                }
            }
            if (!response.isEmpty() && !additionalResponse.isEmpty()) {
                if (butt.getCommandHandler().getMoreList().size() > 0) {
                    return new BotResponse(BotIntention.CHAT, null, response + " [+"
                            + butt.getCommandHandler().getMoreList().size() + " more]", additionalResponse);
                } else {
                    return new BotResponse(BotIntention.CHAT, null, response, additionalResponse);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("UnsupportedEncodingException encountered ", ex.getMessage());
        } catch (IOException ex) {
            log.error("IOException encountered ", ex.getMessage());
        }
        return new BotResponse(BotIntention.CHAT, null, "butt didnt find nothing");
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("g"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

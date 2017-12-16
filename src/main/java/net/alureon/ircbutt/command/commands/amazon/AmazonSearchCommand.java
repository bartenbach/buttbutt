package net.alureon.ircbutt.command.commands.amazon;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.IOException;
import java.net.URLDecoder;
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

        Connection.Response cResponse = null;

        try {
            String amazon = "http://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
            String search = StringUtils.getArgs(cmd);
            cResponse = Jsoup.connect(amazon
                        + URLEncoder.encode(search, "UTF-8")).followRedirects(true).execute();
            Document doc = cResponse.parse();
            Element atfResult = doc.getElementById("atfResults"); // This is where it breaks
            Elements items =
                    atfResult.getElementsByClass("s-access-title");
            int size = items.size();
            for (int i = 0; i < size; i++) {
                Element result = items.get(i);
                Attributes attributes = result.child(0).attributes();
                String url = attributes.get("href");
                if (i == 0) {
                    String urlText = URLDecoder.decode(result.text(), "utf8") + " http://amazon.com" + url;
                    response = new BotResponse(BotIntention.CHAT, null, urlText);
                } else {
                    butt.getCommandHandler().addMore(URLDecoder.decode(result.text(), "utf-8")
                            + " http://amazon.com" + url);
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

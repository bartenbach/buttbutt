package net.alureon.ircbutt.command.commands.google;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides functionality for searching Google images in an IRC channel.
 */
public final class GoogleImageSearchCommand implements Command {

    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger(GoogleImageSearchCommand.class);

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        BotResponse response = new BotResponse(BotIntention.CHAT, null, "couldn't get url");
        try {
            response = new BotResponse(BotIntention.CHAT, null, "https://google.com/search?tbm=isch&q="
                    + URLEncoder.encode(StringUtils.getArgs(cmd), "utf8"));
        } catch (UnsupportedEncodingException ex) {
            log.info("Failed to get image search url: ", ex);
        }
        return response;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("gi"));
    }
}

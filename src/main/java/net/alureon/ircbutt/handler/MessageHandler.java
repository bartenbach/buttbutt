package net.alureon.ircbutt.handler;

import com.google.common.base.Preconditions;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.ButtMath;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(MessageHandler.class);


    public MessageHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleMessage(MessageEvent event) {
        /* Handle a command */
        BotResponse response = new BotResponse(event);
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            response = butt.getCommandHandler().handleCommand(event, event.getMessage().split(" "), response);
            butt.getResponseHandler().handleResponse(response);

        /* Handle karma */
        } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;") || event.getMessage().endsWith("--")
                || event.getMessage().endsWith("--;")) {
            // todo handle karma
        } else if (event.getMessage().startsWith("%s/")) {
            // todo search and replace
        } else {
            /* Check for URL or troll them */
            Preconditions.checkArgument(event.getUser().getNick() != null, "event.getUser().getNick() was null");
            butt.getChatLoggingManager().logMessage(event.getUser().getNick(), event.getMessage());
            String message = event.getMessage();
            //butt.getUrlTitleHandler().checkForUrl(event.getChannel(), message);
            KlongUrlTitleHandler.handleUrl(event.getChannel(), event.getMessage());
            if (message.startsWith(butt.getYamlConfigurationFile().getBotName())) {
                butt.getBotChatHandler().buttHighlightChat(event, butt.getButtNameResponseHandler().getButtRespose(event.getUser()));
            } else {
                if (ButtMath.isRandomResponseTime()) {
                    final String buttFormat = butt.getButtReplaceHandler().buttformat(message).trim();
                    if (!buttFormat.equals(message)) {
                        butt.getBotChatHandler().buttChat(event.getChannel(), buttFormat);
                    }
                }
            }
        }

    }

    public void handlePrivateMessage(PrivateMessageEvent event) {
        BotResponse response = new BotResponse(event);
        if (event.getUser().isVerified()) {
            if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
                response = butt.getCommandHandler().handleCommand(event, event.getMessage().split(" "), response);
                response.setPrivateMessageNoOverride();
                butt.getResponseHandler().handleResponse(response);
            } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;") || event.getMessage().endsWith("--")
                    || event.getMessage().endsWith("--;")) {
                // todo handle karma
            }
        }
    }

    public void handleInvalidCommand(User user) {
        butt.getBotChatHandler().buttPM(user, "butt dont kno nothin bout that");
    }
}

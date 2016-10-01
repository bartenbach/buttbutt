package net.alureon.ircbutt.handler;

import com.google.common.base.Preconditions;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.handler.command.karma.KarmaHandler;
import net.alureon.ircbutt.util.ButtMath;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler {


    private IRCbutt butt;
    private final static Logger log = LoggerFactory.getLogger(MessageHandler.class);


    public MessageHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleMessage(MessageEvent event) {
        /* Handle a command */
        BotResponse response = new BotResponse(event);
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            butt.getCommandHandler().handleCommand(event, event.getMessage().split(" "), response);
            ResponseHandler.handleResponse(response);

        /* Handle karma */
        } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;") || event.getMessage().endsWith("--")
                || event.getMessage().endsWith("--;")) {
            KarmaHandler.handleKarma(butt, response, event.getUser(), event.getMessage());

        } else {
            /* Check for URL or troll them */
            Preconditions.checkArgument(event.getUser() != null, "User was null");
            butt.getChatLoggingManager().logMessage(event.getUser().getNick(), event.getMessage());

            // don't troll URL's
            if (KlongUrlTitleHandler.handleUrl(event.getChannel(), event.getMessage())) {
                return;
            }

            // buttify sentence
            if (ButtMath.isRandomResponseTime()) {
                final String buttFormat = butt.getButtReplaceHandler().buttFormat(event.getMessage()).trim();
                if (!buttFormat.equals(event.getMessage()) && buttFormat.contains(" ")) {
                    butt.getBotChatHandler().buttChat(event.getChannel(), buttFormat);
                }
            }
        }
    }

    public void handlePrivateMessage(PrivateMessageEvent event) {
        BotResponse response = new BotResponse(event);
        Preconditions.checkArgument(event.getUser() != null, "User was null");
        if (butt.getYamlConfigurationFile().getBotNoVerify() || event.getUser().isVerified()) {
            if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
                butt.getCommandHandler().handleCommand(event, event.getMessage().split(" "), response);
                response.setPrivateMessageNoOverride();
                ResponseHandler.handleResponse(response);
            } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;") || event.getMessage().endsWith("--")
                    || event.getMessage().endsWith("--;")) {
                KarmaHandler.handleKarma(butt, response, event.getUser(), event.getMessage());
                response.setPrivateMessageNoOverride();
                ResponseHandler.handleResponse(response);
            }
        }
    }

    public void handleInvalidCommand(User user) {
        butt.getBotChatHandler().buttPM(user, "butt dont kno nothin bout that");
    }

    public void handleInvalidCommand(User user, String message) {
        butt.getBotChatHandler().buttPM(user, message);
    }
}

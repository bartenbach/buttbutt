package net.alureon.ircbutt.handler;

import com.google.common.base.Preconditions;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.ButtMath;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
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
        } else {
            /* Anything that isn't a command */
            Preconditions.checkArgument(event.getUser().getNick() != null, "event.getUser().getNick() was null");
            butt.getChatLoggingManager().logMessage(event.getUser().getNick(), event.getMessage());
            boolean reply = ButtMath.isRandomResponseTime();
            String message = event.getMessage();
            if (reply || message.contains("butt")) {
                if (message.startsWith("buttbutt:")) {
                    butt.getButtChatHandler().buttHighlightChat(event, butt.getButtNameResponseHandler().getButtRespose(event.getUser()));
                } else {
                    final String buttFormat = butt.getButtFormatHandler().buttformat(message).trim();
                    if (!buttFormat.equals(message)) {
                        butt.getButtChatHandler().buttChat(event.getChannel(), buttFormat);
                    }
                }
            }
        }

    }

    public void handleInvalidCommand(User user) {
        butt.getButtChatHandler().buttPM(user, "butt dont kno nothin bout that");
    }
}

package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.ButtMath;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class MessageHandler {


    private IRCbutt butt;


    public MessageHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleMessage(MessageEvent<PircBotX> event) {
        /* Handle a command */
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            butt.getCommandHandler().handleButtCommand(event, event.getMessage().split(" "));
        } else {
        /* Anything that isn't a command */
            butt.getChatLoggingManager().logMessage(event.getUser().getNick(), event.getMessage());
            boolean reply = ButtMath.isRandomResponseTime();
            String message = event.getMessage();
            if (reply || message.contains("butt")) {
                if (message.equalsIgnoreCase("butt") || message.startsWith("buttsbutt:")) {
                    butt.getButtNameResponseHandler().buttRespond(event.getChannel(), event.getUser());
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

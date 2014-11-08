package net.alureon.ircbutt.listener;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.ButtMath;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class ChatListener extends ListenerAdapter<PircBotX> {

    private IRCbutt butt;

    public ChatListener(IRCbutt butt) {
        this.butt = butt;
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) {
        /* Handle a command */
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            butt.getCommandHandler().handleButtCommand(event, event.getMessage().split(" "));
        } else {
        /* Anything that isn't a command */
            boolean reply = ButtMath.isRandomResponseTime();
            String message = event.getMessage();
            if (reply || message.contains("butt")) {
                if (message.equalsIgnoreCase("butt") || message.equalsIgnoreCase("buttbutt")) {
                    butt.getButtNameResponseHandler().buttRespond(event);
                } else {
                    final String buttFormat = butt.getButtFormatHandler().buttformat(message).trim();
                    if (!buttFormat.equals(message)) {
                        butt.getButtChatHandler().buttChat(event, buttFormat);
                    }
                }
            }
        }
    }

}

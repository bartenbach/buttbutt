package net.alureon.ircbutt.listener;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.ButtMath;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ChatListener extends ListenerAdapter<PircBotX> {

    private IRCbutt butt;

    public ChatListener(IRCbutt butt) {
        this.butt = butt;
    }

    @Override
    public void onGenericMessage(GenericMessageEvent<PircBotX> event) {
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            //these are reserved for commands.
        } else {
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

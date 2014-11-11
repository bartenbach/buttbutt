package net.alureon.ircbutt.listener;

import net.alureon.ircbutt.IRCbutt;
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
        butt.getMessageHandler().handleMessage(event);
    }

}

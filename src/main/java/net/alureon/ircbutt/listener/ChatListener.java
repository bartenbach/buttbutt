package net.alureon.ircbutt.listener;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatListener extends ListenerAdapter<PircBotX> {

    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(ChatListener.class);


    public ChatListener(IRCbutt butt) {
        this.butt = butt;
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) {
        butt.getMessageHandler().handleMessage(event);
    }

}

package net.alureon.ircbutt.listener;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
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

    @Override
    public void onGenericMessage(GenericMessageEvent<PircBotX> event) {
        if (event instanceof MessageEvent) {
            log.debug("Message is instanceof MESSAGE EVENT");
        } else if (event instanceof PrivateMessageEvent) {
            log.debug("Message is instanceof PRIVATE MESSAGE EVENT");
        }
    }

}

package net.alureon.ircbutt.listener;


import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class GenericMessageEventListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onGenericMessage(GenericMessageEvent<PircBotX> event) {
        if (event instanceof MessageEvent) {
            MessageEvent<PircBotX> messageEvent = (MessageEvent<PircBotX>) event;
        } else if (event instanceof PrivateMessageEvent) {
            PrivateMessageEvent<PircBotX> privateMessageEvent = (PrivateMessageEvent<PircBotX>) event;
        }
    }
}

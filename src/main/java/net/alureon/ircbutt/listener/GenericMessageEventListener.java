package net.alureon.ircbutt.listener;


import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class GenericMessageEventListener extends ListenerAdapter {

    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        if (event instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) event;
        } else if (event instanceof PrivateMessageEvent) {
            PrivateMessageEvent privateMessageEvent = (PrivateMessageEvent) event;
        }
    }
}

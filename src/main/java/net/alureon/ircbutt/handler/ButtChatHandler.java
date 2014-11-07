package net.alureon.ircbutt.handler;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ButtChatHandler {
    //TODO implement a scheduler

    public void buttChat(GenericMessageEvent event, String x) {
        event.respond(x);
    }

    public void buttMe(MessageEvent<PircBotX> event, String x) {
        //event.getBot().
    }
}

package net.alureon.ircbutt.handler;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class ButtChatHandler {
    //TODO implement a scheduler

    public void buttChat(MessageEvent<PircBotX> event, String x) {
        event.getChannel().send().message(x);
    }

    public void buttHighlightChat(MessageEvent<PircBotX> event, String x) {
        event.respond(x);
    }

    public void buttMe(MessageEvent<PircBotX> event, String x) {
        event.getChannel().send().action(x);
    }
}

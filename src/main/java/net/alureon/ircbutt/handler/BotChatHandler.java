package net.alureon.ircbutt.handler;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class BotChatHandler {
    //todo maybe: implement a scheduler?

    public void buttChat(Channel channel, String x) {
        channel.send().message(x);
    }

    //todo why isn't this ever being called?  code duplication somewhere
    public void buttHighlightChat(MessageEvent<PircBotX> event, String x) {
        event.respond(x);
    }

    public void buttMe(Channel channel, String x) {
        channel.send().action(x);
    }

    public void buttPM(User user, String x) {
        user.send().message(x);
    }
}

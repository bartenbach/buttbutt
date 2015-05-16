package net.alureon.ircbutt.handler;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class BotChatHandler {
    //todo maybe: implement a scheduler?

    public void buttChat(Channel channel, String x) {
        channel.send().message(x);
    }

    public void buttHighlightChat(MessageEvent event, String x) {
        event.respond(x);
    }

    public void buttMe(Channel channel, String x) {
        channel.send().action(x);
    }

    public void buttPM(User user, String x) {
        user.send().message(x);
    }
}

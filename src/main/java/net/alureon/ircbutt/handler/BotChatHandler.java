package net.alureon.ircbutt.handler;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class BotChatHandler {
    //todo maybe: implement a scheduler?

    void buttChat(Channel channel, String x) {
        channel.send().message(x);
    }

    void buttPM(User user, String x) {
        user.send().message(x);
    }
}

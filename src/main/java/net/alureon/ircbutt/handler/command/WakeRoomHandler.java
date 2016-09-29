package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class WakeRoomHandler {

    public static void handleWakeRoom(BotResponse response) {
        Channel channel = response.getChannel();
        if (channel != null) {
            StringBuilder sb = new StringBuilder();
            for (User user : channel.getUsers()) {
                if (sb.length() > 0) {
                    sb.append(' ');
                }
                sb.append(user.getNick());
            }
            response.chat(sb.toString());
        }
    }
}

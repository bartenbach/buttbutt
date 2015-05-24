package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import org.pircbotx.User;

/**
 * Created by alureon on 5/14/15. Fixed at 2015-05-24
 */
public class WakeRoomHandler {

    public void handleWakeRoom(BotResponse response) {
        StringBuilder sb = new StringBuilder();
        for (User user : response.getChannel().getUsers()) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(user.getNick());
        }
        response.chat(sb.toString());
    }
}

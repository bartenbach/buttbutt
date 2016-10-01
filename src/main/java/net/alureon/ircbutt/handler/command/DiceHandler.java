package net.alureon.ircbutt.handler.command;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.BotResponse;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class DiceHandler {

    public static void handleDice(GenericMessageEvent event, BotResponse response) {
        if (event instanceof MessageEvent) {
            Channel channel = ((MessageEvent) event).getChannel();
            ImmutableSortedSet<User> users = channel.getUsers();
            String victimName = "";
            int totalUsers = users.size();
            int victimIndex = (int) (Math.random()*totalUsers);
            int i = 0;
            for (User u : users) {
                if (i < victimIndex) {
                    i++;
                } else if (i == victimIndex) {
                    victimName = u.getNick();
                    break;
                }
            }

            String message1;
            if ((Math.random()*100) < 50) {
                message1 = Colors.WHITE + "rolls a huge " + totalUsers + " sided die and it flattens " + Colors.YELLOW + victimName
                        + Colors.WHITE + " before coming to a halt on " + Colors.RED + "YOU LOSE" + Colors.NORMAL;
            } else {
                message1 = Colors.WHITE + "rolls a huge " + totalUsers + " sided die and it lands near " + Colors.YELLOW + victimName
                        + Colors.WHITE + " before coming to a halt on " + Colors.GREEN + "YOU WIN" + Colors.NORMAL;
            }
            response.me(message1);
        }
    }

}

package net.alureon.ircbutt.handler;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Colors;
import org.pircbotx.User;

public class DiceHandler {


    private IRCbutt butt;


    public DiceHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public BotResponse handleDice(BotResponse response, ImmutableSortedSet<User> users) {
        /* Why they chose to return an ImmutableSortedSet here is completely beyond me.  This is about to
           get straight up disgusting. */
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
        double rand = Math.random()*100;
        if (rand < 50) {
            message1 = Colors.WHITE + "rolls a huge " + totalUsers + " sided die and it flattens " + Colors.YELLOW + victimName
                    + Colors.WHITE + " before coming to a halt on " + Colors.RED + "YOU LOSE" + Colors.NORMAL;
        } else {
            message1 = Colors.WHITE + "rolls a huge " + totalUsers + " sided die and it lands near " + Colors.YELLOW + victimName
                    + Colors.WHITE + " before coming to a halt on " + Colors.GREEN + "YOU WIN" + Colors.NORMAL;
        }
        response.me(message1);
        return response;
    }
}

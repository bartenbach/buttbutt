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
        String message1 = "rolls a huge " + totalUsers + " sided die and it flattens " + victimName;
        String message2 = "before coming to a halt on " + Colors.RED + "YOU LOSE";
        response.me(message1, message2);
        return response;
    }
}

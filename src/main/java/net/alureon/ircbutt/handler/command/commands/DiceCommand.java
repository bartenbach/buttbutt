package net.alureon.ircbutt.handler.command.commands;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.handler.command.Executable;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.Random;

/**
 * DiceCommand holds the functionality for the !dice command, which rolls a fictitious die
 * and has it land on someone in the channel.  The die reads "You win" or "You lose", which
 * is chosen completely at random.
 */
public final class DiceCommand implements Executable {

    /**
     * Prevent instantiation.
     */
    private DiceCommand() {

    }

    /**
     * Executes the !dice command and returns the result.
     * @param butt
     * @param response
     * @param cmd
     * @return
     */
    public BotResponse executeCommand(final IRCbutt butt, BotResponse response, String[] cmd) {
        if (event instanceof MessageEvent) {
            Channel channel = ((MessageEvent) event).getChannel();
            ImmutableSortedSet<User> users = channel.getUsers();
            String victimName = "";
            int totalUsers = users.size();
            Random random = new Random();
            int victimIndex = random.nextInt(totalUsers);
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
            if ((Math.random() * 100) < 50) {
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

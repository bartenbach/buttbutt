package net.alureon.ircbutt.command.commands;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.MathUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * DiceCommand holds the functionality for the !dice command, which rolls a fictitious die
 * and has it land on someone in the channel.  The die reads "You win" or "You lose", which
 * is chosen completely at random.
 */
public final class DiceCommand implements Command {

    /**
     * The !dice command uses Random() to roll the dice.  This is the number that
     * is passed to new Random().  This will yield values from 1 - 100 (if I remember
     * how Random() generates random numbers anyway)
     */
    private static final int DICE_MATH_MAX = 101;
    /**
     * If the rolled number is LESS than this number, the user will "lose" the dice roll.
     * This is set to 50 to provide a completely fair roll.
     */
    private static final int DICE_MATH_MID = 50;

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
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

            String message;
            if (MathUtils.getRandom(0, DICE_MATH_MAX) < DICE_MATH_MID) {
                message = Colors.WHITE + "rolls a huge " + totalUsers + " sided die and it flattens "
                        + Colors.YELLOW + victimName + Colors.WHITE + " before coming to a halt on "
                        + Colors.RED + "YOU LOSE" + Colors.NORMAL;
            } else {
                message = Colors.WHITE + "rolls a huge " + totalUsers + " sided die and it lands near "
                        + Colors.YELLOW + victimName + Colors.WHITE + " before coming to a halt on "
                        + Colors.GREEN + "YOU WIN" + Colors.NORMAL;
            }
            return new BotResponse(BotIntention.ME, null, message);
        } else {
            return new BotResponse(BotIntention.PRIVATE_MESSAGE, event.getUser(),
                    butt.getYamlConfigurationFile().getBotName() + " can't roll no dice here");
        }
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("dice"));
    }

}

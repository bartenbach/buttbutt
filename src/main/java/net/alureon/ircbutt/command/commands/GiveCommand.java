package net.alureon.ircbutt.command.commands;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class GiveCommand {

    public static String handleGive(BotResponse response, GenericMessageEvent event, User user, String[] args) {
        if (event instanceof MessageEvent) {
            MessageEvent event2 = (MessageEvent) event;
            if (args.length > 2) {
                if (recipientExists(event2, args[1])) {
                    response.chat(args[1] + ": " + StringUtils.getArgsOverOne(args));
                    return args[1] + ": " + StringUtils.getArgsOverOne(args);
                }
            } else {
                return "!give <user> <text|command>";
            }
        }
        return null;
    }

    private static boolean recipientExists(MessageEvent event, String recipient) {
        ImmutableSortedSet<User> users = event.getChannel().getUsers();
        for (User x : users) {
            if (x.getNick().equals(recipient))
                return true;
        }
        return false;
    }
}

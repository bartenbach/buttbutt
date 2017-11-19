package net.alureon.ircbutt.handler.command;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class GiveHandler {

    public static String handleGive(BotResponse response, GenericMessageEvent event, User user, String[] args) {
        if (event instanceof MessageEvent) {
            MessageEvent event2 = (MessageEvent) event;
            if (args.length > 2) {
                if (recipientExists(event2, args[1])) {
                    //String parsed = EchoHandler.parseCommands(butt, response, StringUtils.getArgsOverOne(args), user.getNick());
                    response.chat(args[1] + ": " + StringUtils.getArgsOverOne(args));
                    return args[1] + ": " + StringUtils.getArgsOverOne(args);
                    //response.chat(args[1] + ": " + StringUtils.getArgsOverOne(args));
                }
            } else {
                return "!give <user> <command>";
                //response.privateMessage(user, "!give <user> <thing>");
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

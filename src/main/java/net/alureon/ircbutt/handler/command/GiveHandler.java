package net.alureon.ircbutt.handler.command;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Created by alureon on 5/10/15.
 */
public class GiveHandler {

    private IRCbutt butt;

    public GiveHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleGive(BotResponse response, GenericMessageEvent event, User user, String[] args) {
        if (event instanceof MessageEvent) {
            MessageEvent event2 = (MessageEvent) event;
            if (args.length > 2) {
                if (recipientExists(event2, args[1])) {
                    String parsed = butt.getEchoHandler().parseMessage(response, StringUtils.getArgs(args).split(" "));
                    response.chat(args[1] + ": " + parsed);
                }
            } else {
                response.privateMessage(user, "!give <user> <thing>");
            }
        }
    }

    private boolean recipientExists(MessageEvent event, String recipient) {
        ImmutableSortedSet<User> users = event.getChannel().getUsers();
        for (User x : users) {
            if(x.getNick().equals(recipient))
                return true;
        }
        return false;
    }
}

package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(CommandHandler.class);


    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleChatCommand(MessageEvent<PircBotX> event, String[] cmd) {

    }

    public void handlePMCommand(PrivateMessageEvent<PircBotX> event, String[] cmd) {

    }

    public void handleButtCommand(MessageEvent<PircBotX> event, String[] cmd) {
        /* For the sake of clearer code, let's just set these immediately */
        User user = event.getUser();
        Channel channel = event.getChannel();
        String nick = user.getNick();

        /* if it's prefixed with a tilde it's a knowledge request */
        if (cmd[0].startsWith("~")) {
            butt.getKnowledgeHandler().handleKnowledge(cmd, channel, user, nick);
            return;
        }

        /* remove the '!' from the command */
        cmd[0] = cmd[0].replaceFirst("!", "");

        switch (cmd[0]) {
            case "rq":
            case "grab":
            case "q":
            case "qinfo":
            case "qsay":
            case "qsearch":
            case "qfind":
                butt.getQuoteGrabHandler().handleQuoteGrabs(cmd, channel, user, nick);
                return;
            case "learn":
            case "forget":
            case "fact":
                butt.getKnowledgeHandler().handleKnowledge(cmd, channel, user, nick);
                return;
        }

        /*  Other functions, and odds and ends that are easier hard-coded than adding to a database.. */
        switch (cmd[0]) {
            case "bot":
                butt.getButtChatHandler().buttMe(channel, "is a bot!  *does the robot*");
                break;
            case "g":
                if (cmd.length > 1) {
                    String link = "http://www.google.com/search?q=" + StringUtils.concatenateUrlArgs(cmd);
                    butt.getButtChatHandler().buttChat(channel, link);
                }
                break;
            case "yt":
                String link = "http://www.youtube.com/results?search_query=" + StringUtils.concatenateUrlArgs(cmd);
                butt.getButtChatHandler().buttMe(channel, link);
                break;
            case "slap":
                if (cmd.length == 1) {
                    butt.getButtChatHandler().buttMe(channel, "slaps " + nick + " with a large trout");
                } else {
                    butt.getButtChatHandler().buttMe(channel, "slaps " + StringUtils.getArgs(cmd) + " with a large trout");
                }
                break;
            case "sexy":
                if (cmd.length == 1) {
                    butt.getButtChatHandler().buttChat(channel, nick + " is a sexy beast! Meeeow!");
                } else {
                    butt.getButtChatHandler().buttChat(channel, StringUtils.getArgs(cmd) + " is a sexy beast! Meeeow!");
                }
                break;
            case "version":
                butt.getButtChatHandler().buttChat(channel, butt.getProgramName() + " " + butt.getProgramVersion());
                break;
            case "dice":
            case "roll":
                butt.getDiceHandler().handleDice(channel, event.getChannel().getUsers());
                break;
            case "bloat":
                if (cmd.length == 1) {
                    butt.getButtChatHandler().buttChat(channel, "Everything is bloat.");
                } else {
                    butt.getButtChatHandler().buttChat(channel, StringUtils.getArgs(cmd) + " is bloat.");
                }
                break;
            case "gn":
                butt.getButtChatHandler().buttChat(channel, "Good night to all from " + nick);
                break;
            case "gm":
                butt.getButtChatHandler().buttChat(channel, "Good morning to all from " + nick);
                break;
            case "oss":
                butt.getButtChatHandler().buttMe(channel, "is an open source project");
                butt.getButtChatHandler().buttMe(channel, butt.getSourceRepository());
                break;
            case "fd": {
                int random = (int) (Math.random() * 7) + 2;
                butt.getButtChatHandler().buttChat(channel, Colors.TEAL + "buttbutt found " + random + " diamonds!");
                break;
            }
            case "dance":
                butt.getButtChatHandler().buttMe(channel, "does the robot");
                break;
            case "random": {
                int random = (int) (Math.random() * 1000000);
                butt.getButtChatHandler().buttChat(channel, random + " is a random number");
                break;
            }
            case "drink":
                if (cmd.length < 2) {
                    butt.getButtChatHandler().buttChat(channel, "Have a drink, " + nick);
                } else {
                    butt.getButtChatHandler().buttChat(channel, "Have a drink, " + StringUtils.getArgs(cmd));
                }
                break;
            case "poop":
                butt.getButtChatHandler().buttMe(channel, "lets out a big slicker *plop!*");
                break;
        }
    }
}

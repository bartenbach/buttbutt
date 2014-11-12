package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(CommandHandler.class);


    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleButtCommand(MessageEvent<PircBotX> event, String[] cmd) {
        /* For the sake of clearer code, let's just set these immediately */
        User user = event.getUser();
        Channel channel = event.getChannel();
        String nick = user.getNick();

        switch (cmd[0]) {
            case "rq":
            case "grab":
            case "q":
            case "qinfo":
            case "qsay":
            case "qsearch":
            case "qfind":
                butt.getQuoteGrabHandler().handleQuoteGrab(cmd, channel, user, nick);
                return;
            case "learn":
            case "forget":
                butt.getKnowledgeHandler().handleKnowledge(cmd, channel, user, nick);
        }

        /* TODO - This could certainly be split up somehow. */
        if (cmd[0].equals("!bot")) {
            butt.getButtChatHandler().buttMe(channel, "is a robot!");
            butt.getButtChatHandler().buttMe(channel, "does the robot");
        } else if (cmd[0].equals("!g")) {
            if (cmd.length > 1) {
                String link = "http://www.google.com/search?q=" + StringUtils.concatenateUrlArgs(cmd);
                butt.getButtChatHandler().buttChat(channel, link);
            }
        } else if (cmd[0].equals("!yt")) {
            String link = "http://www.youtube.com/results?search_query=" + StringUtils.concatenateUrlArgs(cmd);
            butt.getButtChatHandler().buttMe(channel, link);
        } else if (cmd[0].equals("!notch")) {
            butt.getButtChatHandler().buttMe(channel, "Notch is the creator of minecraft.");
            butt.getButtChatHandler().buttMe(channel, "http://www.minecraftwiki.net/wiki/Notch");
        } else if (cmd[0].equals("!hal")) {
            butt.getButtChatHandler().buttChat(channel, "I'm sorry Dave, I'm afraid I can't do that.");
        } else if (cmd[0].equals("!slap")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttMe(channel, "slaps " + nick + " with a large trout");
            } else {
                    butt.getButtChatHandler().buttMe(channel, "slaps " + StringUtils.getArgs(cmd) + " with a large trout");
            }
        } else if (cmd[0].equals("!rage")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(channel, "we are in the midst of a rage quit");
            } else {
                butt.getButtChatHandler().buttChat(channel, "i sense a rage quit involving " + StringUtils.getArgs(cmd) + "..");
            }
        } else if (cmd[0].equals("!sexy")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(channel, nick + " is a sexy beast! Meeeow!");
            } else {
                butt.getButtChatHandler().buttChat(channel, StringUtils.getArgs(cmd) + " is a sexy beast! Meeeow!");
            }
        } else if (cmd[0].equals("!f")) {
            butt.getButtChatHandler().buttChat(channel, Colors.RED + "" + Colors.BOLD + "AHHH THE FAILURE! IT BURRRRNS!!");
        } else if (cmd[0].equals("!halp")) {
            butt.getButtChatHandler().buttChat(channel, "admin pls");
        } else if (cmd[0].equals("!respond")) {
            butt.getButtChatHandler().buttChat(channel, "pls respond");
        } else if (cmd[0].equals("!version")) {
            butt.getButtChatHandler().buttChat(channel, butt.getProgramName() + " " + butt.getProgramVersion());
        } else if (cmd[0].equals("!dice") || cmd[0].equals("!roll")) {
            butt.getDiceHandler().handleDice(channel, event.getChannel().getUsers());
        } else if (cmd[0].equals("!bloat")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(channel, "Everything is bloat.");
            } else {
                butt.getButtChatHandler().buttChat(channel, StringUtils.getArgs(cmd) + " is bloat.");
            }
        } else if (cmd[0].equals("!gn")) {
            butt.getButtChatHandler().buttChat(channel, "Good night to all from " + nick);
        } else if (cmd[0].equals("!gm")) {
            butt.getButtChatHandler().buttChat(channel, "Good morning to all from " + nick);
        } else if (cmd[0].equals("!nou")) {
            butt.getButtChatHandler().buttChat(channel, "Actually, the thing that you have just accused me of is more applicable to yourself.");
        } else if (cmd[0].equals("!oss")) {
            butt.getButtChatHandler().buttMe(channel, "is an open source project");
            butt.getButtChatHandler().buttMe(channel, butt.getSourceRepository());
        } else if (cmd[0].equals("!fd")) {
            int random = (int) (Math.random()*7)+2;
            butt.getButtChatHandler().buttChat(channel, Colors.TEAL + "buttbutt found " + random + " diamonds!");
        } else if (cmd[0].equals("!hai")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(channel, "ohai");
            } else {
                butt.getButtChatHandler().buttChat(channel, "hai there " + StringUtils.getArgs(cmd));
            }
        } else if (cmd[0].equals("!dance")) {
            butt.getButtChatHandler().buttMe(channel, "does the robot");
        } else if (cmd[0].equals("!random")) {
            int random = (int) (Math.random() * 1000000);
            butt.getButtChatHandler().buttChat(channel, random + " is a random number");
        } else if (cmd[0].equals("!drink")) {
            if (cmd.length < 2) {
                butt.getButtChatHandler().buttChat(channel, "Have a drink, " + nick);
            } else {
                butt.getButtChatHandler().buttChat(channel, "Have a drink, " + StringUtils.getArgs(cmd));
            }
        } else if (cmd[0].equals("!poop")) {
            butt.getButtChatHandler().buttMe(channel, "lets out a big slicker *plop!*");
        }
    }
}

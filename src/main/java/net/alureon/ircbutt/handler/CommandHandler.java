package net.alureon.ircbutt.handler;

import com.google.common.collect.ImmutableSortedSet;
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

        /* TODO - This could certainly be split up somehow. */
        if (cmd[0].equals("!bot")) {
            butt.getButtChatHandler().buttMe(channel, "is a robot!");
            butt.getButtChatHandler().buttMe(channel, "does the robot");
        } else if (cmd[0].equals("!g")) {
            if (cmd.length > 1) {
                String link = "http://www.google.com/search?q=" + StringUtils.concatenateUrlArgs(cmd);
                butt.getButtChatHandler().buttChat(channel, link);
            }
/*        } else if (cmd[0].equals("!grab")){
            if (cmd.length == 2) {
                if (cmd[1].equalsIgnoreCase(event.getUser().getNick())) {
                    butt.getButtChatHandler().buttChat(event, "You like grabbing yourself, " + event.getUser().getNick() + "?");
                } else {
                    if (butt.getChatLoggingManager().hasQuoteFrom(cmd[1])) {
                        String quote = butt.getChatLoggingManager().getLastQuoteFrom(cmd[1]);
                        try {
                            if (!butt.getQuoteGrabTable().quoteAlreadyExists(cmd[1], quote)) {
                                butt.getQuoteGrabTable().addQuote(cmd[1], quote, sender.getName());
                                butt.getButtChatHandler().buttChat(sender.getName() + ": Tada!", 30L);
                            } else {
                                butt.getLogger().info("Attempted to add duplicate quote - not adding duplicate.");
                            }
                        } catch (SQLException ex) {
                            System.out.println("SQL Exception encountered.");
                            ex.printStackTrace();
                        }
                    } else {
                        butt.getButtChatHandler().buttChat(sender.getName() + ": i don't believe I've met " + cmd[1], 30L);
                    }
                }
            } else {
                butt.getButtChatHandler().buttMe("!grab <player>");
            }
        } else if (cmd[0].equals("!rq")) {
            if (cmd.length == 1) {
                String quote = butt.getQuoteGrabTable().getRandomQuote();
                if (quote != null) {
                    butt.getButtChatHandler().buttMe(quote);
                } else {
                    butt.getButtChatHandler().buttChat("Error: couldn't retrieve any quotes!");
                }
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().getRandomQuoteFromPlayer(cmd[1]);
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(quote);
                    } else {
                        butt.getButtChatHandler().buttChat("butt don't know " + cmd[1]);
                    }
                } catch (SQLException ex) {
                    butt.getButtChatHandler().buttChat("error - butt find nothing... :(");
                    ex.printStackTrace();
                }
            }
        } else if (cmd[0].equals("!q")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttMe("!q <player>");
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().getLastQuoteFromPlayer(cmd[1]);
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(quote);
                    } else {
                        butt.getButtChatHandler().buttChat("butt don't know " + cmd[1]);
                    }
                } catch (SQLException ex) {
                    butt.getButtChatHandler().buttChat("error - butt find nothing... :(");
                    ex.printStackTrace();
                }
            }
        } else if (cmd[0].equals("!qinfo")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttMe("!q <id>");
            } else {
                try {
                    String[] quote = butt.getQuoteGrabTable().getQuoteInfo(Integer.parseInt(cmd[1]));
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(quote[0]);
                        butt.getButtChatHandler().buttMe(quote[1]);
                    } else {
                        butt.getButtChatHandler().buttChat("no quote record with id of " + ChatColor.DARK_RED + cmd[1] + ChatColor.RESET);
                    }
                } catch (SQLException ex) {
                    butt.getButtChatHandler().buttChat("error - butt find nothing... :(");
                    ex.printStackTrace();
                }
            }
        } else if (cmd[0].equals("!qsay")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttMe("!qsay <id>");
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().getQuoteById(Integer.parseInt(cmd[1]));
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(quote);
                    } else {
                        butt.getButtChatHandler().buttChat("no quote record with id of " + ChatColor.DARK_RED + cmd[1] + ChatColor.RESET);
                    }
                } catch (SQLException ex) {
                    butt.getButtChatHandler().buttChat("error - butt find nothing... :(");
                    ex.printStackTrace();
                }
            }
        } else if (cmd[0].equals("!qfind") || cmd[0].equals("!qsearch")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttMe("!qfind <string>");
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().findQuote(StringUtils.getArgs(cmd));
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(quote);
                    } else {
                        butt.getButtChatHandler().buttChat("sry butt find noting");
                    }
                } catch (SQLException ex) {
                    butt.getButtChatHandler().buttChat("error - butt find nothing... :(");
                    ex.printStackTrace();
                }
            }*/
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
            /* Why they chose to return an ImmutableSortedSet here is completely beyond me.  This is about to
                get straight up disgusting. */
            ImmutableSortedSet<User> users = event.getChannel().getUsers();
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
            butt.getButtChatHandler().buttMe(channel, "rolls a huge " + totalUsers + " sided die and it flattens "
                    + victimName);
            butt.getButtChatHandler().buttMe(channel, "before coming to a halt on " + Colors.RED + "YOU LOSE");
        } else if (cmd[0].equals("!bloat")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(channel, "Everything is bloat.");
            } else {
                butt.getButtChatHandler().buttChat(channel, StringUtils.getArgs(cmd) + " is bloat.");
            }
        } else if (cmd[0].equals("!gn")) {
            butt.getButtChatHandler().buttChat(channel, "Good night to all from " + nick);
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
        } else if (cmd[0].equals("!insult")) {
/*            if (cmd.length >= 2) {
                butt.getInsultHandler().insultPlayer(cmd);
            }*/
        } else if (cmd[0].equals("!random")) {
            int random = (int) (Math.random() * 1000000);
            butt.getButtChatHandler().buttChat(channel, random + " is a random number");
        } else if (cmd[0].equals("!learn")) {
            if (channel.isOp(user)) {
                butt.getKnowledgeHandler().addKnowledge(nick, cmd);
                butt.getButtChatHandler().buttHighlightChat(event, "ok got it!");
            }
        } else if (cmd[0].equals("!forget")) {
            if (channel.isOp(user)) {
                boolean success = butt.getKnowledgeHandler().removeKnowledge(cmd);
                if (success) {
                    butt.getButtChatHandler().buttChat(channel, "ok butt wont member that no more");
                } else {
                    butt.getMessageHandler().handleInvalidCommand(user);
                }
            } else {
               log.trace(event.getUser().getNick() + " is not an IRC op");
            }
        } else if (cmd[0].startsWith("~")) {
            cmd[0] = cmd[0].substring(1, cmd[0].length());
            log.debug(cmd[0]);
            String info = butt.getKnowledgeHandler().getKnowledge(cmd);
            if (info != null) {
                butt.getButtChatHandler().buttChat(channel, info);
            } else {
                butt.getMessageHandler().handleInvalidCommand(user);
            }
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

package net.alureon.ircbutt.handler;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandHandler {

    private IRCbutt butt;

    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleButtCommand(MessageEvent<PircBotX> event, String[] cmd) {
        String commandSender = event.getUser().getNick();
        if (cmd[0].equals("!bot")) {
            butt.getButtChatHandler().buttMe(event, "is a robot!");
            butt.getButtChatHandler().buttMe(event, "does the robot");
        } else if (cmd[0].equals("!g")) {
            if (cmd.length > 1) {
                String link = "http://www.google.com/search?q=" + StringUtils.concatenateUrlArgs(cmd);
                butt.getButtChatHandler().buttChat(event, link);
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
            butt.getButtChatHandler().buttMe(event, link);
        } else if (cmd[0].equals("!notch")) {
            butt.getButtChatHandler().buttMe(event, "Notch is the creator of minecraft.");
            butt.getButtChatHandler().buttMe(event, "http://www.minecraftwiki.net/wiki/Notch");
        } else if (cmd[0].equals("!hal")) {
            butt.getButtChatHandler().buttChat(event, "I'm sorry Dave, I'm afraid I can't do that.");
        } else if (cmd[0].equals("!slap")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttMe(event, "slaps " + commandSender + " with a large trout");
            } else {
                    butt.getButtChatHandler().buttMe(event, "slaps " + StringUtils.getArgs(cmd) + " with a large trout");
            }
        } else if (cmd[0].equals("!rage")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(event, "we are in the midst of a rage quit");
            } else {
                butt.getButtChatHandler().buttChat(event, "i sense a rage quit involving " + StringUtils.getArgs(cmd) + "..");
            }
        } else if (cmd[0].equals("!sexy")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(event, commandSender + " is a sexy beast! Meeeow!");
            } else {
                butt.getButtChatHandler().buttChat(event, StringUtils.getArgs(cmd) + " is a sexy beast! Meeeow!");
            }
        } else if (cmd[0].equals("!f")) {
            butt.getButtChatHandler().buttChat(event, Colors.RED + "" + Colors.BOLD + "AHHH THE FAILURE! IT BURRRRNS!!");
        } else if (cmd[0].equals("!halp")) {
            butt.getButtChatHandler().buttChat(event, "admin pls");
        } else if (cmd[0].equals("!respond")) {
            butt.getButtChatHandler().buttChat(event, "pls respond");
        } else if (cmd[0].equals("!version")) {
            butt.getButtChatHandler().buttChat(event, butt.getProgramName() + " " + butt.getProgramVersion());
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
            butt.getButtChatHandler().buttMe(event, "rolls a huge " + totalUsers + " sided die and it flattens "
                    + victimName);
            butt.getButtChatHandler().buttMe(event, "before coming to a halt on " + Colors.RED + "YOU LOSE");
        } else if (cmd[0].equals("!bloat")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(event, "Everything is bloat.");
            } else {
                butt.getButtChatHandler().buttChat(event, StringUtils.getArgs(cmd) + " is bloat.");
            }
        } else if (cmd[0].equals("!gn")) {
            butt.getButtChatHandler().buttChat(event, "Good night to all from " + commandSender);
        } else if (cmd[0].equals("!nou")) {
            butt.getButtChatHandler().buttChat(event, "Actually, the thing that you have just accused me of is more applicable to yourself.");
        } else if (cmd[0].equals("!oss")) {
            butt.getButtChatHandler().buttMe(event, "is an open source project");
            butt.getButtChatHandler().buttMe(event, butt.getSourceRepository());
        } else if (cmd[0].equals("!fd")) {
            int random = (int) (Math.random()*7)+2;
            butt.getButtChatHandler().buttChat(event, Colors.TEAL + "buttbutt found " + random + " diamonds!");
        } else if (cmd[0].equals("!hai")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttChat(event, "ohai");
            } else {
                butt.getButtChatHandler().buttChat(event, "hai there " + StringUtils.getArgs(cmd));
            }
        } else if (cmd[0].equals("!dance")) {
            butt.getButtChatHandler().buttMe(event, "does the robot");
        } else if (cmd[0].equals("!insult")) {
/*            if (cmd.length >= 2) {
                butt.getInsultHandler().insultPlayer(cmd);
            }*/
        } else if (cmd[0].equals("!random")) {
            int random = (int) (Math.random() * 1000000);
            butt.getButtChatHandler().buttChat(event, random + " is a random number");
        } else if (cmd[0].equals("!learn")) {
/*            butt.getKnowledgeHandler().addKnowledge(sender.getName(), cmd);
            butt.getButtChatHandler().buttChat("ok " + sender.getName() + " got it", 30L);*/
        } else if (cmd[0].equals("!unlearn")) {
/*            boolean success = butt.getKnowledgeHandler().removeKnowledge(cmd);
            if (success) {
                butt.getButtChatHandler().buttChat(event, "ok butt wont member that no more");
            } else {
                butt.getButtChatHandler().buttChat(event, "butt dont kno nothin bout that");
            }*/
        } else if (cmd[0].startsWith("~")) {
/*            cmd[0] = cmd[0].substring(1, cmd[0].length());
            String info = butt.getKnowledgeHandler().getKnowledge(cmd);
            if (info != null) {
                butt.getButtChatHandler().buttChat(event, info);
            } else {
                butt.getButtChatHandler().buttChat(event, "butt dun kno nothin bout that");
            }*/
        } else if (cmd[0].equals("!drink")) {
            if (cmd.length < 2) {
                butt.getButtChatHandler().buttChat(event, "Have a drink, " + commandSender);
            } else {
                butt.getButtChatHandler().buttChat(event, "Have a drink, " + StringUtils.getArgs(cmd));
            }
        } else if (cmd[0].equals("!poop")) {
            butt.getButtChatHandler().buttMe(event, "lets out a big slicker *plop!*");
        }
    }
}

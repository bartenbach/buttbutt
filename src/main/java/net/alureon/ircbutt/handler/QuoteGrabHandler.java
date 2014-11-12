package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class QuoteGrabHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(QuoteGrabHandler.class);


    public QuoteGrabHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleQuoteGrab(String[] cmd, Channel channel, User user, String nick) {
        if (cmd[0].equals("!grab")){
            if (cmd.length == 2) {
                if (cmd[1].equalsIgnoreCase(nick)) {
                    butt.getButtChatHandler().buttChat(channel, "You like grabbing yourself, " + nick + "?");
                } else {
                    if (butt.getChatLoggingManager().hasQuoteFrom(cmd[1])) {
                        String quote = butt.getChatLoggingManager().getLastQuoteFrom(cmd[1]);
                        try {
                            if (!butt.getQuoteGrabTable().quoteAlreadyExists(cmd[1], quote)) {
                                butt.getQuoteGrabTable().addQuote(cmd[1], quote, nick);
                                butt.getButtChatHandler().buttChat(channel, nick + ": Tada!");
                            } else {
                                log.info("Attempted to add duplicate quote - not adding duplicate.");
                            }
                        } catch (SQLException ex) {
                            log.error("Exception accessing database: ", ex);
                        }
                    } else {
                        butt.getButtChatHandler().buttChat(channel, "i don't believe I've met " + cmd[1]);
                    }
                }
            } else {
                butt.getButtChatHandler().buttPM(user, "!grab <player>");
            }
        } else if (cmd[0].equals("!rq")) {
            if (cmd.length == 1) {
                String quote = butt.getQuoteGrabTable().getRandomQuote();
                if (quote != null) {
                    butt.getButtChatHandler().buttMe(channel, quote);
                } else {
                    butt.getButtChatHandler().buttPM(user, "Error: couldn't retrieve any quotes!");
                }
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().getRandomQuoteFromPlayer(cmd[1]);
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(channel, quote);
                    } else {
                        butt.getButtChatHandler().buttPM(user, "butt don't know " + cmd[1]);
                    }
                } catch (SQLException ex) {
                    log.error("Exception accessing database: ", ex);
                }
            }
        } else if (cmd[0].equals("!q")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttPM(user, "!q <player>");
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().getLastQuoteFromPlayer(cmd[1]);
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(channel, quote);
                    } else {
                        butt.getButtChatHandler().buttPM(user, "butt don't know " + cmd[1]);
                    }
                } catch (SQLException ex) {
                    log.error("Exception accessing database: ", ex);
                }
            }
        } else if (cmd[0].equals("!qinfo")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttPM(user, "!q <id>");
            } else {
                try {
                    String[] quote = butt.getQuoteGrabTable().getQuoteInfo(Integer.parseInt(cmd[1]));
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(channel, quote[0]);
                        butt.getButtChatHandler().buttMe(channel, quote[1]);
                    } else {
                        butt.getButtChatHandler().buttPM(user, "no quote record with id of " + Colors.RED + cmd[1]);
                    }
                } catch (SQLException ex) {
                    log.error("Exception accessing database: ", ex);
                }
            }
        } else if (cmd[0].equals("!qsay")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttPM(user, "!qsay <id>");
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().getQuoteById(Integer.parseInt(cmd[1]));
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(channel, quote);
                    } else {
                        butt.getButtChatHandler().buttPM(user, "no quote record with id of " + Colors.RED + cmd[1]);
                    }
                } catch (SQLException ex) {
                    log.error("Exception accessing database: ", ex);
                }
            }
        } else if (cmd[0].equals("!qfind") || cmd[0].equals("!qsearch")) {
            if (cmd.length == 1) {
                butt.getButtChatHandler().buttPM(user, "!qfind <string>");
            } else {
                try {
                    String quote = butt.getQuoteGrabTable().findQuote(StringUtils.getArgs(cmd));
                    if (quote != null) {
                        butt.getButtChatHandler().buttMe(channel, quote);
                    } else {
                        butt.getButtChatHandler().buttPM(user, "sry butt find noting");
                    }
                } catch (SQLException ex) {
                    log.error("Exception accessing database: ", ex);
                }
            }
        }
    }
}

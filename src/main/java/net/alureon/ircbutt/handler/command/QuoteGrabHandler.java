package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class QuoteGrabHandler {


    private IRCbutt butt;
    private final static Logger log = LoggerFactory.getLogger(QuoteGrabHandler.class);


    public QuoteGrabHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleQuoteGrabs(BotResponse response, String[] cmd, User user, String nick) {
        switch (cmd[0]) {
            case "grab":
                if (cmd.length == 2) {
                    if (cmd[1].equalsIgnoreCase(nick)) {
                        response.highlightChat(response.getRecipient(), "You like grabbing yourself " + nick + "?");
                    } else if (cmd[1].equalsIgnoreCase(butt.getYamlConfigurationFile().getBotName())) {
                        response.highlightChat(response.getRecipient(), "get your hands off me, creep!");
                    } else {
                        if (butt.getChatLoggingManager().hasQuoteFrom(cmd[1])) {
                            String quote = butt.getChatLoggingManager().getLastQuoteFrom(cmd[1]);
                            log.trace("Quote grabbed: " + quote);
                            try {
                                if (!butt.getQuoteGrabTable().quoteAlreadyExists(cmd[1], quote)) {
                                    butt.getQuoteGrabTable().addQuote(cmd[1], quote, nick);
                                    response.highlightChat(user, "Tada!");
                                } else {
                                    log.info("Attempted to add duplicate quote - not adding duplicate.");
                                }
                            } catch (SQLException ex) {
                                log.error("Exception accessing database: ", ex);
                            }
                        } else {
                            response.chat("i don't believe i've met " + cmd[1]);
                        }
                    }
                } else {
                    response.privateMessage(user, "!grab <player>");
                }
                break;
            case "rq":
                if (cmd.length == 1) {
                    String quote = butt.getQuoteGrabTable().getRandomQuote();
                    if (quote != null) {
                        response.chat(quote);
                    } else {
                        log.error("Error: couldn't retrieve a random quote!");
                        response.chat("Uh oh...something's broken");
                    }
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getRandomQuoteFromPlayer(cmd[1]);
                        if (quote != null) {
                            response.chat(quote);
                        } else {
                            response.privateMessage(user, "butt couldn't find anything for " + cmd[1]);
                            log.warn("Attempted to get quote from: " + cmd[1]);
                        }
                    } catch (SQLException ex) {
                        response.noResponse();
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "q":
                if (cmd.length == 1) {
                    response.privateMessage(user, "!q <nick>");
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getLastQuoteFromPlayer(cmd[1]);
                        if (quote != null) {
                            response.chat(quote);
                        } else {
                            response.privateMessage(user, "butt couldn't find anything for " + cmd[1]);
                            log.warn("Attempted to get quote from: " + cmd[1]);
                        }
                    } catch (SQLException ex) {
                        response.noResponse();
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "qinfo":
            case "qi":
                if (cmd.length == 1) {
                    response.privateMessage(user, "!qinfo <id>");
                } else {
                    try {
                        String[] quote = butt.getQuoteGrabTable().getQuoteInfo(Integer.parseInt(cmd[1]));
                        if (quote != null) {
                            response.chat(quote[0], quote[1]);
                        } else {
                            response.privateMessage(user, "no quote record with id " + cmd[1]);
                        }
                    } catch (SQLException ex) {
                        response.noResponse();
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "qsay":
                if (cmd.length == 1) {
                    response.privateMessage(user, "!qsay <id>");
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getQuoteById(Integer.parseInt(cmd[1]));
                        if (quote != null) {
                            response.chat(quote);
                        } else {
                            response.privateMessage(user, "no quote record with id of " + cmd[1]);
                        }
                    } catch (SQLException ex) {
                        response.noResponse();
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "qfind":
            case "qsearch":
            case "qf":
                if (cmd.length == 1) {
                    response.privateMessage(user, "!qfind <string>");
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().findQuote(StringUtils.getArgs(cmd));
                        if (quote != null) {
                            response.chat(quote);
                        } else {
                            response.privateMessage(user, "sry butt find noting");
                        }
                    } catch (SQLException ex) {
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
        }
    }
}

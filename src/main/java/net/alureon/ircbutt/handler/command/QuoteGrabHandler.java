package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.User;

import java.sql.SQLException;

/**
 * The QuoteGrabHandler class provides a method for handling all QuoteGrab-related commands.
 */
public class QuoteGrabHandler {


    /**
     * The instance of the IRCbutt object.
     */
    private IRCbutt butt;
    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger(QuoteGrabHandler.class);


    /**
     * Constructor simply sets a reference to the IRCbutt singleton.
     *
     * @param butt The IRCbutt reference
     */
    public QuoteGrabHandler(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * This function handles the argument parsing specific to QuoteGrab functionality.
     *
     * @param response The BotReponse object
     * @param cmd      The command from the user
     * @param user     The user who gave the command
     * @param nick     The user's nick
     */
    public void handleQuoteGrabs(final BotResponse response, final String[] cmd, final User user, final String nick) {
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
                    String quote = butt.getQuoteGrabTable().getRandomQuoteAndUser();
                    if (quote != null) {
                        response.chat(quote);
                    } else {
                        log.error("Error: couldn't retrieve a random quote!");
                        response.chat("Uh oh...something's broken");
                    }
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getRandomQuoteAndUserFromUser(cmd[1]);
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
            case "rqn":
            case "rqnouser":
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
                        String quote = butt.getQuoteGrabTable().getRandomQuoteFromUser(cmd[1]);
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
                    String quote = butt.getQuoteGrabTable().getQuoteById(Integer.parseInt(cmd[1]));
                    if (quote != null) {
                        response.chat(quote);
                    } else {
                        response.privateMessage(user, "butt don't find no quote with id " + cmd[1]);
                    }
                }
                break;
            case "qfind":
            case "qsearch":
            case "qf":
                if (cmd.length == 1) {
                    response.privateMessage(user, "!qfind <string>");
                } else {
                    String quote = butt.getQuoteGrabTable().findQuote(StringUtils.getArgs(cmd));
                    if (quote != null) {
                        response.chat(quote);
                    } else {
                        response.privateMessage(user, "butt find noting");
                    }
                }
                break;
            default:
                break;
        }
    }
}

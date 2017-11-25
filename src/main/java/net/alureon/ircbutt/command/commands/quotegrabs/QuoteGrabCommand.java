package net.alureon.ircbutt.command.commands.quotegrabs;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The QuoteGrabCommand class provides a method for handling all QuoteGrab-related commands.
 */
public final class QuoteGrabCommand implements Command {

    /**
     * The logger for the class.
     */
    private static final Logger log = LogManager.getLogger(QuoteGrabCommand.class);

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        //TODO this class shouldn't be handling SQL exceptions.  That makes no sense.
        //TODO this needs more refactoring than I have left in me right now.
        switch (cmd[0]) {
            case "grab":
                if (cmd.length == 2) {
                    if (cmd[1].equalsIgnoreCase(event.getUser().getNick())) {
                        return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(),
                                "You like grabbing yourself " + event.getUser().getNick() + "?");
                    } else if (cmd[1].equalsIgnoreCase(butt.getYamlConfigurationFile().getBotName())) {
                        return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(),
                                "get your hands off me, creep!");
                    } else {
                        if (butt.getChatStorage().hasQuoteFrom(cmd[1])) {
                            String quote = butt.getChatStorage().getLastQuoteFrom(cmd[1]);
                            log.trace("Quote grabbed: " + quote);
                            try {
                                if (!butt.getQuoteGrabTable().quoteAlreadyExists(cmd[1], quote)) {
                                    butt.getQuoteGrabTable().addQuote(cmd[1], quote, event.getUser().getNick());
                                    return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "Tada!");
                                } else {
                                    log.debug("User tried to add duplicate quote - not adding duplicate.");
                                    return new BotResponse(BotIntention.NO_REPLY, null, null);
                                }
                            } catch (SQLException ex) {
                                log.error("Exception accessing database: ", ex);
                            }
                        } else {
                            return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(),
                                    "who's " + cmd[1] + "?");
                        }
                    }
                } else {
                    return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!grab <nick>");
                }
                break;
            case "rq":
                if (cmd.length == 1) {
                    String quote = butt.getQuoteGrabTable().getRandomQuoteAndUser();
                    return getQuoteResponse(quote, "butt don't have any quotes yet!", event.getUser(),
                            "Database returned no quote - got null");
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getRandomQuoteAndUserFromUser(cmd[1]);
                        return getQuoteResponse(quote, "butt don't have any quotes for " + cmd[1],
                                event.getUser(), "Didn't find any quotes from user: " + cmd[1]);
                    } catch (SQLException ex) {
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "rqn":
            case "rqnouser":
                if (cmd.length == 1) {
                    String quote = butt.getQuoteGrabTable().getRandomQuote();
                    return getQuoteResponse(quote, "butt don't have any quotes yet!", event.getUser(),
                            "Database returned no quote - got null");
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getRandomQuoteFromUser(cmd[1]);
                        return getQuoteResponse(quote, "butt don't have any quotes from " + cmd[1], event.getUser(),
                                "Database returned no quotes for user: " + cmd[1]);
                    } catch (SQLException ex) {
                        log.error("Exception accessing database: ", ex.getMessage());
                    }
                }
                break;
            case "q":
                if (cmd.length == 1) {
                    return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!q <nick>");
                } else {
                    try {
                        String quote = butt.getQuoteGrabTable().getLastQuoteFromPlayer(cmd[1]);
                        return getQuoteResponse(quote, "butt dont' have any quotes from " + cmd[1], event.getUser(),
                                "Database returned no quotes for user: " + cmd[1]);
                    } catch (SQLException ex) {
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "qinfo":
            case "qi":
                if (cmd.length == 1) {
                    return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!qinfo <id>");
                } else {
                    try {
                        String[] quote = butt.getQuoteGrabTable().getQuoteInfo(Integer.parseInt(cmd[1]));
                        return getQuoteResponse(quote, "butt found no quote with id " + cmd[1], event.getUser(),
                                "Found no quote with id of " + cmd[1] + " in the database.");
                    } catch (SQLException ex) {
                        log.error("Exception accessing database: ", ex);
                    }
                }
                break;
            case "qsay":
                if (cmd.length == 1) {
                    return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!qsay <id>");
                } else {
                    String quote = butt.getQuoteGrabTable().getQuoteById(Integer.parseInt(cmd[1]));
                    return getQuoteResponse(quote, "butt don't find no quote with id " + cmd[1], event.getUser(),
                            "Found no quote in the database with id: " + cmd[1]);
                }
            case "qfind":
            case "qsearch":
            case "qf":
                if (cmd.length == 1) {
                    return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!qfind <string>");
                } else {
                    String quote = butt.getQuoteGrabTable().findQuote(StringUtils.getArgs(cmd));
                    return getQuoteResponse(quote, "butt didnt find nothin", event.getUser(),
                            "Found no quotes matching the search string: " + StringUtils.arrayToString(cmd));
                }
            default:
                break;
        }
        return new BotResponse(BotIntention.CHAT, null, "this shouldn't happen. fell through entire"
                + " quotegrabcommand without hitting a branch");
    }

    /**
     * Helper method that standardizes the way we respond to quote queries.
     * @param quote The quote (or null String) that we retrieved from the database.
     * @param noQuoteMessage The message we will give the user if we found no quotes.
     * @param user The user to highlight.
     * @param logMessage The message we will log to the console.
     * @return the bot's response based on the input and whether or not the quote was null.
     */
    private BotResponse getQuoteResponse(final String quote, final String noQuoteMessage, final User user,
                                         final String logMessage) {
        if (quote != null) {
            return new BotResponse(BotIntention.CHAT, null, quote);
        } else {
            log.debug(logMessage);
            return new BotResponse(BotIntention.HIGHLIGHT, user, noQuoteMessage);
        }
    }

    /**
     * Helper method that standardizes the way we respond to quote queries.
     * @param quote The array of data we retrieved from the database.
     * @param noQuoteMessage The message we will give the user if we found no quotes.
     * @param user The user to highlight.
     * @param logMessage The message we will log to console.
     * @return the bot's response based on the input and whether or not the quote was null.
     */
    private BotResponse getQuoteResponse(final String[] quote, final String noQuoteMessage, final User user,
                                         final String logMessage) {
         if (quote != null) {
            return new BotResponse(BotIntention.CHAT, null, quote[0], quote[1]);
        } else {
            log.debug(logMessage);
            return new BotResponse(BotIntention.HIGHLIGHT, user, noQuoteMessage);
        }
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return (ArrayList<String>) Arrays.asList("qfind", "qsearch", "qf", "rq", "qsay", "qinfo", "qi", "q", "rqn",
                "rqnouser", "grab");
    }
}

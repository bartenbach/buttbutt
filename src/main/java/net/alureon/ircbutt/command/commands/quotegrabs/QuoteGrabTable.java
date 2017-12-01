package net.alureon.ircbutt.command.commands.quotegrabs;

import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides an interface for working with SQL-related Quotegrab functionality.
 */
public final class QuoteGrabTable {


    /**
     * The IRCbutt instance field for getting values from the YAML configuration file.
     */
    private IRCbutt butt;
    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * The class constructor.
     * @param butt The IRCbutt instance needed to access the YAML configuration file.
     */
    public QuoteGrabTable(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * Adds a quote to the bot's quotegrab table.
     * @param nick The nickname of the person who said the quote.
     * @param quote The quote the person said.
     * @param grabber The person who grabbed the quote.
     */
    void addQuote(final String nick, final String quote, final String grabber) {
        String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` (user,quote,grabbed_by) VALUES(?,?,?)";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            log.trace(nick + quote + grabber);
            Object[] objects = {nick, quote, grabber};
            butt.getSqlManager().prepareStatement(ps, objects);
            if (ps != null) {
                ps.executeUpdate();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable.  Unable to add quote.");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred in QuoteGrabTable: ", ex.getMessage());
        }
    }

    /**
     * Checks to see if the quote already exists in the database to prevent double grabs.
     * @param nickname The nickname of the person who said the quote.
     * @param quote The quote that they said.
     * @return True if the quote is already in the database, otherwise false.
     */
    boolean quoteAlreadyExists(final String nickname, final String quote) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` WHERE user=? AND quote=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, nickname);
                ps.setString(2, quote);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    rs.close();
                    return true;
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable. Unable to check to see if quote exists.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Returns a String containing a random quote from the database, and the user who said it
     * in the format "name: quote".
     * @return The user and the quote that was captured.
     */
    String getRandomQuoteAndUser() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ResultSet rs = butt.getSqlManager().getResultSet(ps);
        try {
            if (rs != null) {
                if (rs.next()) {
                    String user = rs.getString("user");
                    String quote = rs.getString("quote");
                    return restructureQuote(user, quote);
                }
            } else {
                log.error("Received null ResultSet in QuoteGrabTable. Unable to retrieve quote from the database.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: ", ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a completely random quote from the database, without the user who
     * said the quote.
     * @return A random quote from the database.
     */
    String getRandomQuote() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ResultSet rs = butt.getSqlManager().getResultSet(ps);
        try {
            if (rs != null) {
                if (rs.next()) {
                    return rs.getString("quote");
                }
            } else {
                log.error("Received null ResultSet in QuoteGrabTable.  Cannot retrieve quote from the database.");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. StackTrace:", ex);
        }
        return null;
    }

    /**
     * Retrieves a quote from the database by its quote ID.  Quote ID's can be retrieved by
     * using !qinfo.
     * @param id The id of the quote to retrieve.
     * @return The quote from the database with the specified ID.
     */
    String getQuoteById(final int id) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE id=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String user = rs.getString("user");
                    String quote = rs.getString("quote");
                    rs.close();
                    return restructureQuote(id, user, quote);
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable.  Cannot retrieve quote from database.");
            }
        } catch (SQLException ex) {
            log.error("Unable to retrieve quote from database by id.\n{}", ex.getMessage());
        }
        return null;
    }

    /**
     * Searches the database for a quote containing the specified String.
     * @param search The string to search the database for.
     * @return Any quote found matching the search.
     */
    String findQuote(final String search) {
        butt.getCommandHandler().getMoreList().clear();
        String firstResult = null;
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` WHERE quote LIKE ?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, "%" + search + "%");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("user");
                    String quote = rs.getString("quote");
                    firstResult = restructureQuote(id, user, quote);
                    while (rs.next()) {
                        id = rs.getInt("id");
                        user = rs.getString("user");
                        quote = rs.getString("quote");
                        butt.getCommandHandler().addMore(restructureQuote(id, user, quote));
                    }
                    rs.close();
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable. Cannot retrieve quote from database.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: " + ex.getMessage());
        }
        if (butt.getCommandHandler().getMoreList().size() > 0) {
            return firstResult + " [+" + butt.getCommandHandler().getMoreList().size() + " more]";
        }
        return firstResult;
    }

    /**
     * Retrieves a random quote and the user who said it, from a specific user, from the database.
     * @param username The name of the user we want a quote from.
     * @return The name of the user and quote in a String (structured through restructureQuote method).
     */
    String getRandomQuoteAndUserFromUser(final String username) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` WHERE user=? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String quote = rs.getString("quote");
                    rs.close();
                    return restructureQuote(username, quote);
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable. Cannot retrieve quote from database.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Gets a random quote from a specified user, without their username attached to it.
     * @param username The username to get a random quote from.
     * @return A String containing a random quote from the user, or null if none exists.
     */
    String getRandomQuoteFromUser(final String username) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` WHERE user=? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String value = rs.getString("quote");
                    rs.close();
                    return value;
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable. Unable to retrieve quote from database.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieve the info for a quote from the database.  This includes the user who said the quote, the quote
     * that they said, who grabbed the quote, and when it was grabbed.
     * @param id The id of the quote to find.
     * @return A String array, with element 0 containing the quote, and element 1 containing the quote info. If no
     * quote can be found, this will return null.
     */
    String[] getQuoteInfo(final int id) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE id=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String user = rs.getString("user");
                    String quote = rs.getString("quote");
                    String grabber = rs.getString("grabbed_by");
                    String time = rs.getString("timestamp");
                    String formattedQuote = restructureQuote(id, user, quote);
                    String quoteInfo = "Grabbed by: " + grabber + " on " + time;
                    String[] quotes = new String[2];
                    quotes[0] = formattedQuote;
                    quotes[1] = quoteInfo;
                    rs.close();
                    return quotes;
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable. Cannot retrieve quote info.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the last quote that was grabbed from the user.
     * @param username The username to get the last quote for.
     * @return A String containing either the last quote grabbed from the user, or null if none exists.
     */
    String getLastQuoteFromUser(final String username) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_quotes` WHERE user=? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String quote = rs.getString("quote");
                    rs.close();
                    return restructureQuote(username, quote);
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in QuoteGrabTable. Cannot get last quote from user.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in QuoteGrabTable: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Restructures a quote in a human-readable format.
     * @param id The ID of the quote.
     * @param username The user who said the quote.
     * @param quote The quote the user said.
     * @return The human-readable, restructured quote.
     */
    private String restructureQuote(final int id, final String username, final String quote) {
        return "(" + id + ") " + username + ": " + quote;
    }

    /**
     * Restructures a quote in a human-readable format.
     * @param username The user who said the quote.
     * @param quote The quote the user said.
     * @return The human-readable, restructured quote.
     */
    private String restructureQuote(final String username, final String quote) {
        return username + ": " + quote;
    }

}

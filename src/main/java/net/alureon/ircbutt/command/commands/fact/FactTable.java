package net.alureon.ircbutt.command.commands.fact;

import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains functions for working with the bot's Fact table in SQL.
 */
public final class FactTable {


    /**
     * The IRCbutt instance for accessing the database.
     */
    private IRCbutt butt;
    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * Constructor for this object accepts a reference to the IRCbutt object.
     * @param butt The IRCbutt singleton
     */
    public FactTable(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * Inserts new knowledge into the database.
     * @param item The KEY we are adding to the fact database.
     * @param data The corresponding VALUE we are adding to the fact database.
     * @param creator The user's nickname who is creating the fact.
     */
    void insertKnowledge(final String item, final String data, final String creator) {
        String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` (item,data,added_by) VALUES(?,?,?)";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            if (ps != null) {
                ps.setString(1, item);
                ps.setString(2, data);
                ps.setString(3, creator);
                ps.executeUpdate();
            } else {
                log.error("Received null PreparedStatement in FactTable.  Cannot insert knowledge into database.");
            }
        } catch (SQLException ex) {
            log.error("Unable to insert knowledge into SQL database. ", ex.getMessage());
        }
    }

    /**
     * Retrieves a fact from the database.
     * @param item The KEY we are searching the database for.
     * @return The VALUE the database holds for said key.
     */
    public String queryKnowledge(final String item) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            Object[] objects = {item};
            butt.getSqlManager().prepareStatement(ps, objects);
            ResultSet rs = butt.getSqlManager().getResultSet(ps);
            assert rs != null;
            if (rs.next()) {
                return rs.getString("data");
            }
        } catch (SQLException ex) {
            log.error("Failed to query knowledge database. ", ex.getMessage());
        }
        return null;
    }

    /**
     * Deletes the specified KEY and associated VALUE from the database.
     * @param item The item to delete.
     * @return A boolean indicating TRUE if the fact was deleted.
     */
    boolean deleteKnowledge(final String item) {
        log.debug(item);
        String update = "DELETE FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            if (ps != null) {
                ps.setString(1, item);
                int rows = ps.executeUpdate();
                return rows > 0; // if no rows have been updated then we haven't actually deleted anything
            } else {
                log.error("Received null PreparedStatement in FactTable.  Cannot delete knowledge.");
            }
        } catch (SQLException ex) {
            log.error("Failed to delete knowledge from database. ", ex.getMessage());
        }
        return false;
    }

    /**
     * Retrieves a completely random fact's VALUE from the database.
     * @return the VALUE of a random fact.
     */
    String getRandomData() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` ORDER BY RAND() LIMIT 1";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
             ResultSet rs = butt.getSqlManager().getResultSet(ps)) {
            if (rs != null) {
                if (rs.next()) {
                    return rs.getString("data");
                }
            } else {
                log.error("Received null ResultSet in FactTable.  Cannot retrieve random fact.");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. ", ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a completely random fact's VALUE from the database.
     * @return the VALUE of a random fact.
     */
    public String getRandomFactName() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` ORDER BY RAND() LIMIT 1";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
             ResultSet rs = butt.getSqlManager().getResultSet(ps)) {
            if (rs != null) {
                if (rs.next()) {
                    return rs.getString("item");
                }
            } else {
                log.error("Received null ResultSet in FactTable.  Cannot retrieve random fact.");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. ", ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the info for a fact from the database.  This information consists of the fact's ID number,
     * the name of the fact, the person who added the fact, and the date it was added on.
     * @param name The name of the fact to get information for (can be an integer).
     * @return A String containing the fact's information.
     */
    String getFactInfo(final String name) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("added_by");
                    String time = rs.getString("timestamp");
                    rs.close();
                    return "(" + id + ") " + name + ": added by " + user + " on " + time;
                } else {
                    try {
                        int idNumber = Integer.parseInt(name);
                        return findFactById(idNumber);
                    } catch (NumberFormatException ex) {
                        // we don't really care.  this might not be a number.
                        log.warn("Tried to parse '" + name + "' as an integer.  I guess it's not.  Who knew?");
                    } finally {
                        rs.close();
                    }
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in FactTable.  Cannot query for fact info.");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception. ", ex.getMessage());
        }
        return null;
    }

    /**
     * Search the fact database for facts.  This search looks for fact DATA that is like the search term,
     * not the fact's name itself.  If there are many, this will load into !more.
     * @param search The string to search the fact database for.
     * @return returns the first result that matches the query.
     */
    String findFact(final String search) {
        butt.getCommandHandler().clearMore();
        String firstResult = null;
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` WHERE (data REGEXP ?) LIMIT 24";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setString(1, search);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    firstResult = getFormattedFact(rs);
                    while (rs.next()) {
                        butt.getCommandHandler().addMore(getFormattedFact(rs));
                    }
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in FactTable.  Unable to search for fact.");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception ", ex.getMessage());
        }
        if (butt.getCommandHandler().getMoreList().size() > 0) {
            return firstResult + " [+" + butt.getCommandHandler().getMoreList().size() + " more]";
        }
        return firstResult;
    }

    /**
     * Helper method for formatting a fact into a readable string.  When a user searches for a fact,
     * this can be used to display the information to the user in a readable format.
     * @param rs The ResultSet object from the database containing the fact information.
     * @return A String, in the form of the fact's ID, the fact name, and the associated data.
     * @throws SQLException Throws any SQLException encountered back up into the calling method.
     */
    private String getFormattedFact(final ResultSet rs) throws SQLException {
        return "(" + rs.getInt("id") + ") " + rs.getString("item") + ": " + rs.getString("data");
    }

    /**
     * Looks up a fact by the fact's ID number, or returns null if none exists.
     * @param fid The fact ID to search the database for.
     * @return The fact with the corresponding ID from the database.
     */
    private String findFactById(final int fid) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` WHERE id = ?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            if (ps != null) {
                ps.setInt(1, fid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("item");
                    String user = rs.getString("added_by");
                    String time = rs.getString("timestamp");
                    rs.close();
                    return "(" + fid + ") " + name + ": added by " + user + " on " + time;
                }
                rs.close();
            } else {
                log.error("Received null PreparedStatement in FactTable.  Cannot lookup fact.");
            }
        } catch (SQLException ex) {
            log.error("Encountered SQL Exception in findFactById ", ex.getMessage());
        }
        return null;
    }

    /**
     * Appends knowledge to an existing fact in the database.
     * @param item The fact name we intend to append to.
     * @param data The data we wish to append.
     */
    void appendKnowledge(final String item, final String data) {
        String update = "UPDATE `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` SET data = CONCAT(data, ?) WHERE item = ?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            if (ps != null) {
                ps.setString(1, data);
                ps.setString(2, item);
                ps.executeUpdate();
            } else {
                log.error("Received null PreparedStatement in FactTable.  Knowledge not appended.");
            }
        } catch (SQLException ex) {
            log.error("Unable to insert knowledge into SQL database. ", ex.getMessage());
        }
    }

}

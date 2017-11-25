package net.alureon.ircbutt.command.commands.karma;

import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides the SQL-related functionality for working with the Karma command.
 */
public final class KarmaTable {


    /**
     * The instance of IRCbutt for accessing the database.
     */
    private IRCbutt butt;
    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();


    /**
     * Constructor sets the class field for IRCbutt.
     * @param butt The IRCbutt instance needed for accessing the database.
     */
    public KarmaTable(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * Checks to see whether we have Karma for an item.
     * @param item The item to check for existing Karma level.
     * @return True if the item is in the database, false if it is not.
     */
    private boolean itemExists(final String item) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            ps.setString(1, item);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            log.error("Failed to check if Karma item '" + item + "' exists: ", ex.getMessage());
        }
        return false;
    }

    /**
     * Decrements an item's Karma level.
     * @param karma The Karma object to decrement in the database.
     */
    void decrementKarma(final Karma karma) {
        if (itemExists(karma.getItem())) {
            String update = "UPDATE `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                    + "_karma` SET karma = karma -1 WHERE item=?";
            updateKarma(update, karma);
        } else {
            String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                    + "_karma` (item,karma) VALUES(?,?)";
            try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
                ps.setString(1, karma.getItem());
                ps.setInt(2, -1);
                ps.execute();
            } catch (SQLException ex) {
                log.error("Failed to update Karma: ", ex.getMessage());
            }
        }
    }

    /**
     * Retrieves an object's current Karma level from the database.
     * @param item The String containing the item we'd like to search for a Karma level for.
     * @return The current Karma level of the item, or null, if it doesn't exist.
     */
    Integer getKarmaLevel(final String item) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            ps.setString(1, item);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int result = rs.getInt("karma");
                rs.close();
                return result;
            }
            rs.close();
        } catch (SQLException ex) {
            log.error("Failed to retrieve karma level for " + item, ex.getMessage());
        }
        return null;
    }

    /**
     * Increments a Karma object's Karma level in the database.
     * @param karma The Karma object we'd like to increment Karma level for.
     */
    void incrementKarma(final Karma karma) {
        if (itemExists(karma.getItem())) {
            String update = "UPDATE `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                    + "_karma` SET karma = karma +1 WHERE item=?";
            updateKarma(update, karma);
        } else {
            String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                    + "_karma` (item,karma) VALUES(?,?)";
            try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
                ps.setString(1, karma.getItem());
                ps.setInt(2, 1);
                ps.execute();
            } catch (SQLException ex) {
                log.error("Failed to increment Karma: ", ex.getMessage());
            }
        }
    }

    /**
     * Convenience function for updating an object's Karma in the database.
     * @param update The SQL string to execute.
     * @param karma The Karma object pertaining to the update operation.
     */
    private void updateKarma(final String update, final Karma karma) {
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            ps.setString(1, karma.getItem());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("Failed to update Karma: ", ex.getMessage());
        }
    }
}

package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains functions for working with the bot's Fact table in SQL.
 */
public class FactTable {


    private IRCbutt butt;
    private static final Logger log = LogManager.getLogger();

    /**
     * Constructor for this object accepts a reference to the IRCbutt object.
     * @param butt The IRCbutt singleton
     */
    public FactTable(final IRCbutt butt) {
        this.butt = butt;
    }

    public void insertKnowledge(String item, String data, String grabber) {
        String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` (item,data,added_by) VALUES(?,?,?)";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            ps.setString(1, item);
            ps.setString(2, data);
            ps.setString(3, grabber);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("Unable to insert knowledge into SQL database.  Stacktrace: ", ex);
        }
    }

    public String queryKnowledge(String item) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            Object[] objects = {item};
            SqlManager.prepareStatement(ps, objects);
            ResultSet rs = SqlManager.getResultSet(ps);
            assert rs != null;
            if (rs.next()) {
                return rs.getString("data");
            }
        } catch (SQLException ex) {
            log.error("Failed to query knowledge database. StackTrace:", ex);
        }
        return null;
    }

    public boolean deleteKnowledge(String item) {
        log.debug(item);
        String update = "DELETE FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            ps.setString(1, item);
            int rows = ps.executeUpdate();
            return rows > 0; // if no rows have been updated then we haven't actually deleted anything
        } catch (SQLException ex) {
            log.error("Failed to delete knowledge from database. StackTrace:", ex);
        }
        return false;
    }

    public String getRandomData() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` ORDER BY RAND() LIMIT 1";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
             ResultSet rs = SqlManager.getResultSet(ps)) {
            assert rs != null;
            if (rs.next()) {
                return rs.getString("data");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. StackTrace:", ex);
        }
        return null;
    }

    public String getFactInfo(String name) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE item=?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String user = rs.getString("added_by");
                String time = rs.getString("timestamp");
                rs.close();
                return "(" + id + ") " + name + ": added by " + user + " on " + time;
            }
            rs.close();
        } catch (SQLException ex) {
            log.error("SQL Exception.  StackTrace:", ex);
        }
        return null;
    }

    public String findFact(String search) {
        butt.getMoreHandler().clearMore();
        String firstResult = null;
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE data LIKE ? LIMIT 24";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                firstResult = getFormattedFact(rs);
                while (rs.next()) {
                    butt.getMoreHandler().addMore(getFormattedFact(rs));
                }
            }
            rs.close();
            //todo try to find a fact with similar name?
        } catch (SQLException ex) {
            log.error("SQL Exception, ", ex);
        }
        return firstResult;
    }

    private String getFormattedFact(ResultSet rs) throws SQLException {
        return "(" + rs.getInt("id") + ") " + rs.getString("item") + ": " + rs.getString("data");
    }

    public String findFactById(int fid) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE id = ?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query)) {
            ps.setInt(1, fid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String item = rs.getString("item");
                String data = rs.getString("data");
                rs.close();
                return "(" + id + ") " + item + ": " + data;
            }
            rs.close();
        } catch (SQLException ex) {
            log.error("SQL Exception, ", ex);
        }
        return null;
    }

    public void appendKnowledge(String item, String data) {
        String update = "UPDATE `" + butt.getYamlConfigurationFile().getSqlTablePrefix()
                + "_knowledge` SET data = CONCAT(data, ?) WHERE item = ?";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            ps.setString(1, data);
            ps.setString(2, item);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("Unable to insert knowledge into SQL database.  Stacktrace: ", ex);
        }
    }

}

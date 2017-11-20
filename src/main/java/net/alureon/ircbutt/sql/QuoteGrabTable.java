package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuoteGrabTable {


    private IRCbutt butt;
    private final static Logger log = LogManager.getLogger();


    public QuoteGrabTable(IRCbutt butt) {
        this.butt = butt;
    }

    public void addQuote(String nick, String quote, String grabber) {
        String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` (user,quote,grabbed_by) VALUES(?,?,?)";
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            log.trace(nick + quote + grabber);
            Object[] objects = {nick, quote, grabber};
            butt.getSqlManager().prepareStatement(ps, objects);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. StackTrace:", ex);
        }
        // null PreparedStatement is handled by getPreparedStatement();
    }

    public boolean quoteAlreadyExists(String playerName, String quote) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? AND quote=?";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ps.setString(1, playerName);
        ps.setString(2, quote);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            rs.close();
            return true;
        }
        rs.close();
        return false;
    }

    public String getRandomQuoteAndUser() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ResultSet rs = butt.getSqlManager().getResultSet(ps);
        try {
            if (rs.next()) {
                String user = rs.getString("user");
                String quote = rs.getString("quote");
                return restructureQuote(user, quote);
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. StackTrace:", ex);
        }
        return null;
    }


    public String getRandomQuote() {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ResultSet rs = butt.getSqlManager().getResultSet(ps);
        try {
            if (rs.next()) {
                return rs.getString("quote");
            }
        } catch (SQLException ex) {
            log.error("SQL Exception has occurred. StackTrace:", ex);
        }
        return null;
    }

    public String getQuoteById(int id) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE id=?";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String user = rs.getString("user");
            String quote = rs.getString("quote");
            rs.close();
            return restructureQuote(id, user, quote);
        }
        rs.close();
        return null;
    }

    public String findQuote(String search) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE quote LIKE ?";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ps.setString(1, "%" + search + "%");
        ResultSet rs = ps.executeQuery();
        //TODO this could certainly return more than one item
        if (rs.next()) {
            int id = rs.getInt("id");
            String user = rs.getString("user");
            String quote = rs.getString("quote");
            rs.close();
            return restructureQuote(id, user, quote);
        }
        rs.close();
        return null;
    }

    public String getRandomQuoteAndUserFromUser(String username) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String quote = rs.getString("quote");
            rs.close();
            return restructureQuote(username, quote);
        }
        rs.close();
        return null;
    }

    public String getRandomQuoteFromUser(String username) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String value = rs.getString("quote");
            rs.close();
            return value;
        }
        rs.close();
        return null;
    }

    public String[] getQuoteInfo(int id) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE id=?";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
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
        return null;
    }

    public String getLastQuoteFromPlayer(String username) throws SQLException {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? ORDER BY id DESC LIMIT 1";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String quote = rs.getString("quote");
            rs.close();
            return restructureQuote(username, quote);
        }
        rs.close();
        return null;
    }

    private String restructureQuote(int id, String username, String quote) {
        return "(" + id + ") " + username + ": " + quote;
    }

    private String restructureQuote(String username, String quote) {
        return username + ": " + quote;
    }

}

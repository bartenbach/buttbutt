package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuoteGrabTable {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(QuoteGrabTable.class);


    public QuoteGrabTable(IRCbutt butt) {
        this.butt = butt;
    }

    public void addQuote(String nick, String quote, String grabber) {
        if (butt.getSqlManager().isConnected()) {
            java.util.Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(date);
            String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` (user,quote,grabbed_by,timestamp) VALUES(?,?,?,?)";
            PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update);
            if (ps != null) {
                log.trace(nick + quote + grabber + timestamp);
                Object[] objects = { nick, quote, grabber, timestamp };
                butt.getSqlManager().prepareStatement(ps, objects);
                try {
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    log.error("SQL Exception has occurred. StackTrace:", ex);
                }
            } // null PreparedStatement is handled by getPreparedStatement();
        } else {
            butt.getSqlManager().reconnect();
            addQuote(nick, quote, grabber);
        }
    }

    public boolean quoteAlreadyExists(String playerName, String quote) throws SQLException{
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? AND quote=?";
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(query);
            ps.setString(1, playerName);
            ps.setString(2, quote);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public String getRandomQuote() {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` ORDER BY RAND() LIMIT 1";
            PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
            ResultSet rs = butt.getSqlManager().getResultSet(ps);
            try {
                if (rs.next()) {
                    String user = rs.getString("user");
                    String quote = rs.getString("quote");
                    int id = rs.getInt("id");
                    return restructureQuote(id, user, quote);
                }
            } catch (SQLException ex) {
                log.error("SQL Exception has occurred. StackTrace:", ex);
            }
        } else {
            butt.getSqlManager().reconnect();
            getRandomQuote();
        }
        return null;
    }

    public String getQuoteById(int id) throws SQLException {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE id=?";
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String user = rs.getString("user");
                String quote = rs.getString("quote");
                return restructureQuote(id, user, quote);
            }
        } else {
            butt.getSqlManager().reconnect();
            getQuoteById(id);
        }
        return null;
    }

    public String findQuote(String search) throws SQLException {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE quote LIKE ?";
            StringBuilder sb = new StringBuilder(query);
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(query);
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            //TODO this could certainly return more than one item
            if (rs.next()) {
                int id = rs.getInt("id");
                String user = rs.getString("user");
                String quote = rs.getString("quote");
                return restructureQuote(id, user, quote);
            }
        } else {
            butt.getSqlManager().reconnect();
            findQuote(search);
        }
        return null;
    }

    public String getRandomQuoteFromPlayer(String username) throws SQLException {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? ORDER BY RAND() LIMIT 1";
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String quote = rs.getString("quote");
                return restructureQuote(id, username, quote);
            }
        } else {
            butt.getSqlManager().reconnect();
            getRandomQuoteFromPlayer(username);
        }
        return null;
    }

    public String[] getQuoteInfo(int id) throws SQLException {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE id=?";
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(query);
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
                return quotes;
            }
        } else {
            butt.getSqlManager().reconnect();
            getQuoteInfo(id);
        }
        return null;
    }

    public String getLastQuoteFromPlayer(String username) throws SQLException {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" +butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` WHERE user=? ORDER BY id DESC LIMIT 1";
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String quote = rs.getString("quote");
                return restructureQuote(id, username, quote);
            }
        } else {
            butt.getSqlManager().reconnect();
            getLastQuoteFromPlayer(username);
        }
        return null;
    }

    private String restructureQuote(int id, String username, String quote) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(id).append(") ").append(username).append(": ").append(quote);
        return sb.toString();
    }

}

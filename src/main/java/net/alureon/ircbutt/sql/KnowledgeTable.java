package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KnowledgeTable {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(KnowledgeTable.class);

    // TODO/FIXME Update this entire class to take advantage of AutoCloseable
    public KnowledgeTable(IRCbutt butt) {
        this.butt = butt;
    }

    public void insertKnowledge(String item, String data, String grabber) throws SQLException {
        if (butt.getSqlManager().isConnected()) {
            java.util.Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(date);
            String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` (item,data,added_by,timestamp) VALUES(?,?,?,?)";
            PreparedStatement ps = butt.getSqlManager().getConnection().prepareStatement(update);
            ps.setString(1, item);
            ps.setString(2, data);
            ps.setString(3, grabber);
            ps.setString(4, timestamp);
            ps.executeUpdate();
        } else {
            butt.getSqlManager().reconnect();
            queryKnowledge(item);
        }
    }

    public String queryKnowledge(String item) {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE item=?";
            PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
            Object[] objects = { item };
            butt.getSqlManager().prepareStatement(ps, objects);
            ResultSet rs = butt.getSqlManager().getResultSet(ps);
            if (rs != null) {
                try {
                    if (rs.next()) {
                        return rs.getString("data");
                    }
                } catch (SQLException ex) {
                    log.error("SQL Exception has occurred. StackTrace:", ex);
                }
            }
        } else {
            butt.getSqlManager().reconnect();
            queryKnowledge(item);
        }
        return null;
    }

    public boolean deleteKnowledge(String item) {
        if (butt.getSqlManager().isConnected()) {
            log.debug(item);
            String update = "DELETE FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` WHERE item=?";
            PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update);
            try {
                ps.setString(1, item);
                int rows = ps.executeUpdate();
                return (rows > 0); // if no rows have been updated then we haven't actually deleted anything
            } catch (SQLException ex) {
                log.error("SQL Exception has occurred. StackTrace:", ex);
            }
        }
        return false;
    }

    public String getRandomData() {
        if (butt.getSqlManager().isConnected()) {
            String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` ORDER BY RAND() LIMIT 1";
            PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
            ResultSet rs = butt.getSqlManager().getResultSet(ps);
            try {
                if (rs.next()) {
                    return rs.getString("data");
                }
            } catch (SQLException ex) {
                log.error("SQL Exception has occurred. StackTrace:", ex);
            }
        } else {
            butt.getSqlManager().reconnect();
            getRandomData();
        }
        return null;
    }

}

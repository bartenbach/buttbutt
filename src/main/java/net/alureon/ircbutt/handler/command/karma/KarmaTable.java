package net.alureon.ircbutt.handler.command.karma;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KarmaTable {


    private IRCbutt butt;
    private final static Logger log = LogManager.getLogger(KarmaTable.class);


    public KarmaTable(IRCbutt butt) {
        this.butt = butt;
    }

    private boolean itemExists(String item) {
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
            log.error("KarmaTable: SQL Exception, ", ex);
        }
        return false;
    }

    void decrementKarma(Karma karma, User user, BotResponse response) {
        if (itemExists(karma.getItem())) {
            String update = "UPDATE `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` SET karma = karma -1 WHERE item=?";
            updateKarma(update, karma, response, user);
        } else {
            String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` (item,karma) VALUES(?,?)";
            try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
                ps.setString(1, karma.getItem());
                ps.setInt(2, -1);
                ps.execute();
            } catch (SQLException ex) {
                log.error("KarmaTable ", ex);
                response.privateMessage(user, "failed to update karma :(");
            }
        }
    }

    Integer getKarmaLevel(String item) {
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
            log.error("KarmaTable: SQL Exception, ", ex);
        }
        return null;
    }

    void incrementKarma(Karma karma, User user, BotResponse response) {
        if (itemExists(karma.getItem())) {
            String update = "UPDATE `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` SET karma = karma +1 WHERE item=?";
            updateKarma(update, karma, response, user);
        } else {
            String update = "INSERT INTO `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` (item,karma) VALUES(?,?)";
            try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
                ps.setString(1, karma.getItem());
                ps.setInt(2, 1);
                ps.execute();
            } catch (SQLException ex) {
                log.error("KarmaTable ", ex);
                response.privateMessage(user, "failed to update karma :(");
            }
        }
    }

    private void updateKarma(String update, Karma karma, BotResponse response, User user) {
        try (PreparedStatement ps = butt.getSqlManager().getPreparedStatement(update)) {
            ps.setString(1, karma.getItem());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("KarmaTable ", ex);
            response.privateMessage(user, "failed to update karma :(");
        }
    }
}

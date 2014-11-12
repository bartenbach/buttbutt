package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SqlManager {


    private Connection connection;
    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(SqlManager.class);


    public SqlManager(IRCbutt butt) {
        this.butt = butt;
    }

    public void connectToDatabase() {
        String ip = butt.getYamlConfigurationFile().getSqlIp();
        String username = butt.getYamlConfigurationFile().getSqlUsername();
        String password = butt.getYamlConfigurationFile().getSqlPassword();
        int port = butt.getYamlConfigurationFile().getSqlPort();
        String database = butt.getYamlConfigurationFile().getSqlDatabase();
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
        log.debug(url);
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            log.error("Failed to establish SQL connection: ", ex);
        }
        log.info("[SQL backend connected]");
    }

    public void createTablesIfNotExists() {
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `user` VARCHAR(16) NOT NULL," +
                "`quote` VARCHAR(200) NOT NULL, `grabbed_by` VARCHAR(16) NOT NULL," +
                "`timestamp` DATETIME NOT NULL) ENGINE=MyISAM");
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `item` VARCHAR(32) NOT NULL UNIQUE," +
                "`data` VARCHAR(200) NOT NULL, `added_by` VARCHAR(16) NOT NULL," +
                "`timestamp` DATETIME NOT NULL) ENGINE=MyISAM");
    }

    public boolean sqlUpdate(String sql) {
        if (this.isConnected()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.executeUpdate();
                return true;
            } catch (SQLException ex) {
                log.error("Unable to update SQL database. Stacktrace: ", ex);
            }
        } else {
            reconnect();
            sqlUpdate(sql);
        }
        return false;
    }

    public void reconnect() {
        log.warn("Disconnected from SQL Database. Reconnecting...");
        if (!this.isConnected()) {
            this.connectToDatabase();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public PreparedStatement getPreparedStatement(String query) {
        try {
            return this.connection.prepareStatement(query);
        } catch (SQLException ex) {
            log.error("Unable to prepare SQL statement. Stacktrace: ", ex);
            return null;
        }
    }

    public void prepareStatement(PreparedStatement ps, Object ... objects) {
        try {
            for (int i = 0 ; i < objects.length ; i++) {
                if (objects[i] instanceof String) {
                    ps.setString((i+1), (String) objects[i]);
                } else {
                    ps.setInt((i+1), Integer.parseInt(String.valueOf(objects[i])));
                }
                i++;
            }
        } catch (SQLException ex) {
            log.error("Failed to set parameter in PreparedStatement. StackTrace: ", ex);
        }
    }

    public ResultSet getResultSet(PreparedStatement ps) {
        try {
            return ps.executeQuery();
        } catch (SQLException ex) {
            log.error("Failed to execute query from PreparedStatement. StackTrace: ", ex);
            return null;
        }
    }

    public boolean isConnected() {
        try {
            return connection.isValid(10);
        } catch (SQLException ex) {
            log.warn("Disconnected from SQL database, attempting reconnection...", ex);
            reconnect();
            return isConnected();
        }
    }

}

package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SqlManager {


    private Connection connection;
    private IRCbutt butt;
    private final static Logger log = LogManager.getLogger();


    public SqlManager(IRCbutt butt) {
        this.butt = butt;
    }

    public void connectToDatabase() {
        String ip = butt.getYamlConfigurationFile().getSqlIp();
        String username = butt.getYamlConfigurationFile().getSqlUsername();
        String password = butt.getYamlConfigurationFile().getSqlPassword();
        int port = butt.getYamlConfigurationFile().getSqlPort();
        String database = butt.getYamlConfigurationFile().getSqlDatabase();
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoreconnect=true";
        log.debug(url);
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            log.error("Failed to establish SQL connection: ", ex);
            return;
        }
        log.info("[SQL backend connected]");
    }

    public void createTablesIfNotExists() {
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_quotes` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `user` VARCHAR(16) NOT NULL," +
                "`quote` VARCHAR(300) NOT NULL, `grabbed_by` VARCHAR(16) NOT NULL," +
                "`timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP) ENGINE=MyISAM");
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_knowledge` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `item` VARCHAR(32) NOT NULL UNIQUE," +
                "`data` VARCHAR(300) NOT NULL, `added_by` VARCHAR(16) NOT NULL," +
                "`timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP) ENGINE=MyISAM");
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `item` VARCHAR(32) NOT NULL," +
                "`karma` SMALLINT NOT NULL) ENGINE=MyISAM");
    }

    private boolean sqlUpdate(String sql) {
        checkConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            log.error("Unable to update SQL database. Stacktrace: ", ex);
        }
        return false;
    }

    private void reconnect() {
        log.warn("Disconnected from SQL Database. Reconnecting...");
        this.connectToDatabase();
    }

    //   public Connection getConnection() {
    //       return this.connection;
    //   }

    public PreparedStatement getPreparedStatement(String query) {
        checkConnection();
        try {
            return this.connection.prepareStatement(query);
        } catch (SQLException | NullPointerException ex) {
            log.error("Unable to prepare SQL statement. Stacktrace: ", ex);
            return null;
        }
    }

    void prepareStatement(PreparedStatement ps, Object... objects) {
        checkConnection();
        try {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof String) {
                    ps.setString((i + 1), (String) objects[i]);
                } else {
                    ps.setInt((i + 1), Integer.parseInt(String.valueOf(objects[i])));
                }
            }
        } catch (SQLException ex) {
            log.error("Failed to set parameter in PreparedStatement. StackTrace: ", ex);
        }
    }

    ResultSet getResultSet(PreparedStatement ps) {
        checkConnection();
        try {
            return ps.executeQuery();
        } catch (SQLException ex) {
            log.error("Failed to execute query from PreparedStatement. StackTrace: ", ex);
            return null;
        }
    }

    private boolean isConnected() {
        try {
            return connection.isValid(10);
        } catch (SQLException ex) {
            log.warn("Exception checking connection validity, ", ex);
            return false;
        }
    }

    private void checkConnection() {
        if (!isConnected()) {
            reconnect();
        }
    }

}

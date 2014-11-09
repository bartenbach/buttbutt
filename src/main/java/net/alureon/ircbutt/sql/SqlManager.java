package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;

import java.sql.*;

import static net.alureon.ircbutt.IRCbutt.log;

public class SqlManager {


    private Connection connection;
    private String username;
    private String password;
    private String ip;
    private String port;
    private String tablePrefix;
    private String database;


    public SqlManager(IRCbutt butt) {
        this.ip = butt.getYamlConfigurationFile().getSqlIp();
        this.username = butt.getYamlConfigurationFile().getSqlUsername();
        this.password = butt.getYamlConfigurationFile().getSqlPassword();
        this.port = butt.getYamlConfigurationFile().getSqlPort();
        this.tablePrefix = butt.getYamlConfigurationFile().getSqlTablePrefix();
        this.database = butt.getYamlConfigurationFile().getSqlDatabase();
    }

    public void connectToDatabase() {
        String url = "jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.database;
        try {
            this.connection = DriverManager.getConnection(url, this.username, this.password);
        } catch (SQLException ex) {
            log.error("Failed to establish SQL connection: ", ex);
        }
    }
    public void createTablesIfNeeded() {
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + this.tablePrefix + "_quotes` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `user` VARCHAR(16) NOT NULL," +
                "`quote` VARCHAR(100) NOT NULL, `grabbed_by` VARCHAR(16) NOT NULL," +
                "`timestamp` DATETIME NOT NULL) ENGINE=MyISAM");
        sqlUpdate("CREATE TABLE IF NOT EXISTS `" + this.tablePrefix + "_knowledge` " +
                "(`id` SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT, `item` VARCHAR(32) NOT NULL UNIQUE," +
                "`data` VARCHAR(200) NOT NULL, `added_by` VARCHAR(16) NOT NULL," +
                "`timestamp` DATETIME NOT NULL) ENGINE=MyISAM");
    }
    public boolean sqlUpdate(String sql) {
        if (this.isConnected()) {
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            } catch (SQLException ex) {
                log.error("Unable to update SQL database.");
            }
        } else {
            reconnect();
            sqlUpdate(sql);
        }
        return false;
    }
    public void reconnect() {
        log.error("Disconnected from SQL Database. Reconnecting...");
        if (!this.isConnected()) {
            this.connectToDatabase();
        }
    }
    public String getTablePrefix() {
        return this.tablePrefix;
    }
    public Connection getConnection() {
        return this.connection;
    }
    public PreparedStatement getPreparedStatement(String query) {
        try {
            return this.connection.prepareStatement(query);
        } catch (SQLException ex) {
            log.error("Unable to prepare SQL statement. Stacktrace:");
            ex.printStackTrace();
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
            log.error("Failed to set parameter in PreparedStatement. StackTrace:");
            ex.printStackTrace();
        }
    }
    public ResultSet getResultSet(PreparedStatement ps) {
        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            log.error("Failed to execute query from PreparedStatement. StackTrace:");
            e.printStackTrace();
            return null;
        }
    }
    public boolean isConnected() {
        try {
            return connection.isValid(10);
        } catch (SQLException ex) {
            reconnect();
            return isConnected();
        }
    }
}

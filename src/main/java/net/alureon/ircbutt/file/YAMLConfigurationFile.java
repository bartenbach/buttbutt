package net.alureon.ircbutt.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Represents a user's YAML configuration file, and all the values they have set.
 */
public final class YAMLConfigurationFile {


    /**
     * The CONFIG.yml file itself.
     */
    private static final File CONFIG = new File("config.yml");
    /**
     * The size of buffer to use when we copy the config.yml from the jar.
     */
    private static final int RESOURCE_BUFFER_SIZE = 4096;
    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * The bot's IRC nickname.
     */
    private String botName;
    /**
     * The bot's IRC nickname (not a real IRC value, but the bot will refer to itself as this).
     */
    private String botNickName;
    /**
     * The bot's IRC 'login' value.
     */
    private String botLogin;
    /**
     * The bot's IRC password.
     */
    private String botPassword;
    /**
     * The bot's IRC 'real name' value.
     */
    private String botRealName;
    /**
     * How long to delay before sending messages.
     */
    private long botMessageDelay;
    /**
     * True if the bot should require uses to be registered to add commands to the database.
     */
    private boolean noVerify;
    /**
     * A list of IRC channels that the bot should join.
     */
    private List<String> channelList;
    /**
     * The hostname of the IRC server to connect to.
     */
    private String serverHostname;
    /**
     * The port to connect to the IRC server on.
     */
    private int serverPort;
    /**
     * If the bot should try to reconnect automatically.
     */
    private boolean serverAutoReconnect;
    /**
     * The username to use to connect to the SQL database.
     */
    private String sqlUsername;
    /**
     * The password to be used to connect to the SQL database.
     */
    private String sqlPassword;
    /**
     * The IP address of the SQL database.
     */
    private String sqlIp;
    /**
     * The port to use to connect to the SQL database.
     */
    private int sqlPort;
    /**
     * The name of the SQL database to create/use.
     */
    private String sqlDatabase;
    /**
     * The table prefix to use when creating tables in the SQL database.
     */
    private String sqlTablePrefix;
    /**
     * If the bot should connect to the IRC server using a secure connection.
     */
    private boolean sslEnabled;
    /**
     * The random response frequency of the bot.  1 in this many chances of buttifying a sentence.
     */
    private int randomResponseFrequency;


    /**
     * Creates a configuration file if one doesn't already exist, then shuts down gracefully.
     */
    public void createConfigIfNotExists() {
        if (!CONFIG.exists()) {
            exportResource();
            log.info("Created new config.yml file in the current directory.");
            log.info("Please edit the configuration file and fill in the appropriate details");
            System.exit(0);
        }
    }

    /**
     * Parses the user's config.yml configuration file and loads all the values into memory.
     */
    @SuppressWarnings("unchecked")  // if the user fucks the config up, that's their problem.
    public void parseConfig() {
        Yaml yaml = new Yaml();
        try (InputStream is = new FileInputStream(CONFIG)) {
            Map<String, Object> map = (Map<String, Object>) yaml.load(is);
            Map<String, Object> botSettings = (Map<String, Object>) map.get("Bot");
            Map<String, Object> serverSettings = (Map<String, Object>) map.get("Server");
            Map<String, Object> sqlSettings = (Map<String, Object>) map.get("SQL");
            this.channelList = (List<String>) map.get("Channels");
            this.botName = (String) botSettings.get("Name");
            this.botLogin = (String) botSettings.get("Login");
            this.botNickName = (String) botSettings.get("Nickname");
            this.botRealName = (String) botSettings.get("Realname");
            this.botPassword = (String) botSettings.get("Password");
            this.botMessageDelay = Long.parseLong(String.valueOf(botSettings.get("Message-Delay")));
            this.noVerify = Boolean.parseBoolean(String.valueOf(botSettings.get("No-Verify")));
            this.randomResponseFrequency =
                    Integer.parseInt(String.valueOf(botSettings.get("Random-Response-Frequency")));
            this.serverHostname = (String) serverSettings.get("Hostname");
            this.serverPort = Integer.parseInt(String.valueOf(serverSettings.get("Port")));
            this.serverAutoReconnect = Boolean.parseBoolean(String.valueOf(serverSettings.get("Auto-Reconnect")));
            this.sslEnabled = Boolean.parseBoolean(String.valueOf(serverSettings.get("SSL")));
            this.sqlUsername = (String) sqlSettings.get("Username");
            this.sqlPassword = (String) sqlSettings.get("Password");
            this.sqlIp = (String) sqlSettings.get("IP");
            this.sqlPort = Integer.parseInt(String.valueOf(sqlSettings.get("Port")));
            this.sqlDatabase = (String) sqlSettings.get("Database");
            this.sqlTablePrefix = (String) sqlSettings.get("Table-Prefix");
        } catch (FileNotFoundException ex) {
            log.error("config.yml not found: ", ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            log.error("Failed to parse config.yml: ", ex.getMessage());
            System.exit(1);
        }
        log.info("[Configuration file loaded]");
    }

    /**
     * Exports the config.yml from the jar into the user's bot directory so they can configure the bot.
     */
    private void exportResource() {
        String resourceName = "/config.yml";
        InputStream stream;
        OutputStream resStreamOut;
        String jarFolder;
        try {
            stream = YAMLConfigurationFile.class.getResourceAsStream(resourceName);
            if (stream == null) {
                throw new IOException("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[RESOURCE_BUFFER_SIZE];
            jarFolder = new File(YAMLConfigurationFile.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
            stream.close();
            resStreamOut.close();
        } catch (IOException | URISyntaxException ex) {
            log.fatal("Failed to export config.yml from jar!");
            System.exit(1);
        }
    }

    /**
     * Returns the bot's nickname from the config.yml.
     * @return The bot's nickname
     */
    public String getBotName() {
        return botName;
    }

    /**
     * Returns the bot's nickname.  This is not the name the bot chats with, this is
     * the nickname of the bot, that it will use to refer to itself with.
     * @return The name the bot refers to itself with.
     */
    public String getBotNickName() {
        return botNickName;
    }

    /**
     * Returns the bot's IRC 'login' name from the config.yml.
     * @return The bot's IRC 'login' name.
     */
    public String getBotLogin() {
        return botLogin;
    }

    /**
     * Returns the bot's IRC 'real name' value from the config.yml.
     * @return The bot's IRC 'real name'
     */
    public String getBotRealName() {
        return botRealName;
    }

    /**
     * Returns the bot's IRC password from the config.yml.
     * @return The bot's IRC password.
     */
    public String getBotPassword() {
        return botPassword;
    }

    /**
     * Returns any message delay the bot should use when sending IRC messages.
     * @return The message delay defined in the config.yml.
     */
    public long getBotMessageDelay() {
        return botMessageDelay;
    }

    /**
     * Returns whether or not users need to be verified to add to the bot's database.
     * @return True if users are required to be verified to add to the bot's database.
     */
    public boolean getBotNoVerify() {
        return noVerify;
    }

    /**
     * Returns the list of channels defined in the config.yml.
     * @return A list of Strings being IRC channels the bot should join.
     */
    public List<String> getChannelList() {
        return channelList;
    }

    /**
     * Gets the IRC server hostname the bot should connect to.
     * @return The IRC server hostname the bot should connect to.
     */
    public String getServerHostname() {
        return serverHostname;
    }

    /**
     * Gets the port of the IRC server the bot should use to connect, as defined in config.yml.
     * @return The port used to connect to IRC as defined in config.yml.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Returns the boolean value defined in the config.yml for ServerAutoReconnect.
     * @return True if the bot should attempt to reconnect to the server automatically.
     */
    public boolean getServerAutoReconnect() {
        return serverAutoReconnect;
    }

    /**
     * Returns the SQL username defined in config.yml.
     * @return The username defined for SQL connections in the config.yml.
     */
    public String getSqlUsername() {
        return sqlUsername;
    }

    /**
     * Returns the SQL password defined in config.yml.
     * @return The password defined for SQL connections in the config.yml.
     */
    public String getSqlPassword() {
        return sqlPassword;
    }

    /**
     * Returns the SQL IP address defined in the config.yml.
     * @return The SQL IP address defined for SQL connections in the config.yml file.
     */
    public String getSqlIp() {
        return sqlIp;
    }

    /**
     * Returns the SQL port defined in the config.yml.
     * @return Returns the port defined in the config.yml for SQL connections.
     */
    public int getSqlPort() {
        return sqlPort;
    }

    /**
     * Returns the SQL database name defined in the config.yml.
     * @return The SQL database name defined in the config.yml to use for database storage.
     */
    public String getSqlDatabase() {
        return sqlDatabase;
    }

    /**
     * Returns the SQL table prefix to be used when creating SQL tables for the bot.
     * @return The table prefix as defined in the config.yml for SQL table creation.
     */
    public String getSqlTablePrefix() {
        return sqlTablePrefix;
    }

    /**
     * Returns the boolean value of SSL in the config.yml.
     * @return True if the bot should use a secure connection to the IRC server.
     */
    public boolean getSSLEnabled() {
        return this.sslEnabled;
    }

    /**
     * Returns the random response frequency of the bot. The bot will have a 1 in this-many chance of responding
     * with a buttified sentence.
     * @return The max number to use for random number generation for random response frequency.
     */
    public int getRandomResponseFrequency() {
        return randomResponseFrequency;
    }
}

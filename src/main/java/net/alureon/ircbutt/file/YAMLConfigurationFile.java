package net.alureon.ircbutt.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

public class YAMLConfigurationFile {


    private final static File config = new File("config.yml");
    final static Logger log = LoggerFactory.getLogger(YAMLConfigurationFile.class);
    private String botName;
    private String botLogin;
    private String botPassword;
    private long botMessageDelay;
    private List<String> channelList;
    private String serverHostname;
    private boolean serverAutoReconnect;
    private String sqlUsername;
    private String sqlPassword;
    private String sqlIp;
    private int sqlPort;
    private String sqlDatabase;
    private String sqlTablePrefix;


    public void createConfigIfNotExists() {
        if (!config.exists()) {
            try {
                exportResource("/config.yml");
                log.info("Created new config.yml file in the current directory.");
            } catch (Exception ex) {
                log.error("Unable to copy configuration file: ", ex);
            }
        }
    }

    @SuppressWarnings("unchecked")  // if the user fucks the config up that's their problem
    public void parseConfig() {
        Yaml yaml = new Yaml();
        try {
            InputStream is = new FileInputStream(config);
            Map<String,Object> map = (Map<String,Object>) yaml.load(is);
            Map<String,Object> botSettings = (Map<String,Object>) map.get("Bot");
            Map<String,Object> serverSettings = (Map<String,Object>) map.get("Server");
            Map<String,Object> sqlSettings = (Map<String,Object>) map.get("SQL");
            this.channelList = (List<String>) map.get("Channels");
            this.botName = (String) botSettings.get("Name");
            this.botLogin = (String) botSettings.get("Login");
            this.botPassword = (String) botSettings.get("Password");
            this.botMessageDelay = Long.parseLong(String.valueOf(botSettings.get("Message-Delay")));
            this.serverHostname = (String) serverSettings.get("Hostname");
            this.serverAutoReconnect = Boolean.parseBoolean(String.valueOf(serverSettings.get("Auto-Reconnect")));
            this.sqlUsername = (String) sqlSettings.get("Username");
            this.sqlPassword = (String) sqlSettings.get("Password");
            this.sqlIp = (String) sqlSettings.get("IP");
            this.sqlPort = Integer.parseInt(String.valueOf(sqlSettings.get("Port")));
            this.sqlDatabase = (String) sqlSettings.get("Database");
            this.sqlTablePrefix = (String) sqlSettings.get("Table-Prefix");
        } catch (FileNotFoundException ex) {
            log.error("config.yml not found!", ex);
            System.exit(1);
        }
        log.info("[Configuration file loaded]");
    }

    private void exportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = YAMLConfigurationFile.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(YAMLConfigurationFile.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (resStreamOut != null) {
                resStreamOut.close();
            }
        }
    }

    public String getBotName() {
        return botName;
    }

    public String getBotLogin() {
        return botLogin;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public long getBotMessageDelay() {
        return botMessageDelay;
    }

    public List<String> getChannelList() {
        return channelList;
    }

    public String getServerHostname() {
        return serverHostname;
    }

    public boolean getServerAutoReconnect() {
        return serverAutoReconnect;
    }

    public String getSqlUsername() {
        return sqlUsername;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public String getSqlIp() {
        return sqlIp;
    }

    public int getSqlPort() {
        return sqlPort;
    }

    public String getSqlDatabase() {
        return sqlDatabase;
    }

    public String getSqlTablePrefix() {
        return sqlTablePrefix;
    }

}

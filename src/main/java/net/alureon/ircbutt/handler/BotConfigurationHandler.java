package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Configuration;

import javax.net.ssl.SSLSocketFactory;

/**
 * Class that represents the Bot's configuration, and instantiates a Configuration object for PircBotX.
 */
public class BotConfigurationHandler {

    /**
     * The instance of IRCbutt for retrieving YAML configuration file values.
     */
    private IRCbutt butt;

    /**
     * Constructor sets the IRCbutt field to the IRCbutt object.
     * @param butt The IRCbutt instance.
     */
    public BotConfigurationHandler(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * Loads all values from the configuration file and returns them as a PircBotX Configuration object.
     * @return the PircBotX Configuration object.
     */
    public Configuration getConfiguration() {
        Configuration.Builder configBuilder = new Configuration.Builder();
        configBuilder.setName(butt.getYamlConfigurationFile().getBotName())
                .setLogin(butt.getYamlConfigurationFile().getBotLogin())
                .setRealName(butt.getYamlConfigurationFile().getBotRealName())
                .setAutoReconnect(butt.getYamlConfigurationFile().getServerAutoReconnect())
                .setMessageDelay(butt.getYamlConfigurationFile().getBotMessageDelay())
                .setNickservPassword(butt.getYamlConfigurationFile().getBotPassword())
                .setListenerManager(butt.getListenerManager())
                .addServer(butt.getYamlConfigurationFile().getServerHostname(),
                        butt.getYamlConfigurationFile().getServerPort())
                .setVersion(butt.getProgramVersion());
        if (butt.getYamlConfigurationFile().getSSLEnabled()) {
            configBuilder.setSocketFactory(SSLSocketFactory.getDefault());
        }
        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            configBuilder.addAutoJoinChannel(x);
        }  // TODO this doesn't authenticate before joining channels
        return configBuilder.buildConfiguration();
    }
}

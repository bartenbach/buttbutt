package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Configuration;

public class BotConfigurationHandler {

    private IRCbutt butt;

    public BotConfigurationHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public Configuration getConfiguration() {
        Configuration.Builder configBuilder = new Configuration.Builder();
        configBuilder.setName(butt.getYamlConfigurationFile().getBotName())
                .setLogin(butt.getYamlConfigurationFile().getBotLogin())
                .setRealName(butt.getYamlConfigurationFile().getBotRealName())
             //   .setServerHostname(butt.getYamlConfigurationFile().getServerHostname()) - DEPRECATED as of 2.1
                .setAutoReconnect(butt.getYamlConfigurationFile().getServerAutoReconnect())
                .setMessageDelay(butt.getYamlConfigurationFile().getBotMessageDelay())
                .setNickservPassword(butt.getYamlConfigurationFile().getBotPassword())
                .setListenerManager(butt.getListenerManager())
                .addServer(butt.getYamlConfigurationFile().getServerHostname(), butt.getYamlConfigurationFile().getServerPort())
                .setVersion(butt.getProgramVersion());
        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            configBuilder.addAutoJoinChannel(x);
        }  // TODO this doesn't authenticate before joining channels
        return configBuilder.buildConfiguration();
    }
}

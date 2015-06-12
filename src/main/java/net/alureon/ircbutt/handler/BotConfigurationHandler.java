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
                .setServerHostname(butt.getYamlConfigurationFile().getServerHostname())
                .setAutoReconnect(butt.getYamlConfigurationFile().getServerAutoReconnect())
                .setMessageDelay(butt.getYamlConfigurationFile().getBotMessageDelay())
                .setNickservPassword(butt.getYamlConfigurationFile().getBotPassword())
                .setListenerManager(butt.getListenerManager())
                .setVersion(butt.getProgramVersion());
/*        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            configBuilder.addAutoJoinChannel(x);
        }*/  // TODO this is commented out because the bot doesn't authenticate before joining channels
        return configBuilder.buildConfiguration();
    }
}

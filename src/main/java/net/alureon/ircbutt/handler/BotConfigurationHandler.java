package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

public class BotConfigurationHandler {

    private IRCbutt butt;

    public BotConfigurationHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public Configuration<PircBotX> getConfiguration() {
        Configuration.Builder<PircBotX> configBuilder = new Configuration.Builder<>();
        configBuilder.setName(butt.getYamlConfigurationFile().getBotName())
                .setLogin(butt.getYamlConfigurationFile().getBotLogin())
                .setRealName(butt.getYamlConfigurationFile().getBotRealName())
                .setServerHostname(butt.getYamlConfigurationFile().getServerHostname())
                .setAutoReconnect(butt.getYamlConfigurationFile().getServerAutoReconnect())
                .setMessageDelay(butt.getYamlConfigurationFile().getBotMessageDelay())
                .setNickservPassword(butt.getYamlConfigurationFile().getBotPassword())
                .setListenerManager(butt.getListenerManager())
                .setVersion(butt.getProgramVersion());
        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            configBuilder.addAutoJoinChannel(x);
        }  // TODO this doesn't authenticate before joining channels
        return configBuilder.buildConfiguration();
    }
}

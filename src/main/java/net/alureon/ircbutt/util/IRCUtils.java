package net.alureon.ircbutt.util;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class IRCUtils {

    public static boolean isOpInBotChannel(IRCbutt butt, User user) {
        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            for (Channel y : user.getChannelsOpIn()) {
                if (y.getName().equalsIgnoreCase(x)) {
                    return true;
                }
            }
        }
        return false;
    }

}

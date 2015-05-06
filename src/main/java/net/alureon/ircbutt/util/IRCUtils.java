package net.alureon.ircbutt.util;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * Created by alureon on 5/5/15.
 */

public class IRCUtils {


    private IRCbutt butt;


    public IRCUtils(IRCbutt butt) {
        this.butt = butt;
    }

    public boolean isOpInBotChannel(User user) {
        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            for (Channel y : user.getChannelsOpIn()) {
                if (y.getChannelKey().equals(x)) {
                    System.out.println("key: " + y.getChannelKey());
                    System.out.println(y.getChannelId().toString());
                    System.out.println(y.toString());
                    return true;
                }
            }
        }
        return false;
    }

}

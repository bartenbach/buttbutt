package net.alureon.ircbutt.util;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * Contains static helper functions for working with IRC channels.
 */
public final class IRCUtils {

    /**
     * Prevent instantiation.
     */
    private IRCUtils() {

    }

    /**
     * Determines if the user specified is a channel operator.  It looks like
     * the way this is written, the user can be an operator in any channel that
     * is defined in the configuration file.  That may not be the current channel.
     * TODO this needs looked at.
     * @param butt The IRCbutt object for access to the YAMLConfigurationFile
     * @param user The user to check
     * @return True if the user is a channel operator, otherwise false.
     */
    public static boolean isOpInBotChannel(final IRCbutt butt, final User user) {
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

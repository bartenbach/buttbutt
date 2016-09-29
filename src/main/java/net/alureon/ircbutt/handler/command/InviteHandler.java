package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.IRCbutt;


public class InviteHandler {

    public static void handleInvite(IRCbutt butt, String[] args) {
        for (String x : butt.getYamlConfigurationFile().getChannelList()) {
            for (String y : args) {
                if (x.equalsIgnoreCase(y)) {
                    butt.getPircBotX().sendIRC().joinChannel(x);
                }
            }
        }
    }
}

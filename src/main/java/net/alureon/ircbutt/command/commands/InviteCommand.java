package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;


public class InviteCommand {

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

package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.IRCbutt;

/**
 * Created by alureon on 5/13/15.
 */

public class InviteHandler {


    private IRCbutt butt;


    public InviteHandler(IRCbutt butt) {
        this.butt = butt;
    }


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

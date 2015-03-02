package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;

/**
 * Created by alureon on 3/1/15.
 */

public class EchoHandler {

    private IRCbutt butt;

    public EchoHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public BotResponse handleEcho(BotResponse response, String[] cmd) {
        return null;
    }
}

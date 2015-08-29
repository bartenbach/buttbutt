package net.alureon.ircbutt.listener;


import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class PrivateMessageListener extends ListenerAdapter<PircBotX> {


    private IRCbutt butt;


    public PrivateMessageListener(IRCbutt butt) {
        this.butt = butt;
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) {
        butt.getMessageHandler().handlePrivateMessage(event);
    }

}

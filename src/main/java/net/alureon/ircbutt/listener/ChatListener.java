package net.alureon.ircbutt.listener;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * The ChatListener is the listener class that utilizes the PircBotX API to
 * listen for chat messages in the IRC channel.  When any message is passed from
 * the listener, we pass it to the IrcMessageHandler.
 */
public class ChatListener extends ListenerAdapter {

    /**
     * The private instance of the IRCbutt object.
     */
    private IRCbutt butt;

    /**
     * Constructor sets the instance of the IRCbutt object.
     * @param butt The IRCbutt object.
     */
    public ChatListener(final IRCbutt butt) {
        this.butt = butt;
    }

    @Override
    public void onMessage(final MessageEvent event) {
        butt.getIrcMessageHandler().handleMessage(event);
    }

}

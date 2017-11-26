package net.alureon.ircbutt.listener;


import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * The listener class for listening to private messages via the PircBotX API.
 */
public final class PrivateMessageListener extends ListenerAdapter {

    /**
     * The instance of IRCbutt for passing messages to the IrcMessageReceiver.
     */
    private IRCbutt butt;

    /**
     * The constructor for the listener.
     * @param butt The IRCbutt instance needed to fetch the IrcMessageReceiver.
     */
    public PrivateMessageListener(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * The listener for private messages via the PircBotX API.
     * @param event Any PrivateMessageEvent fired from the PircBotX API.
     */
    @Override
    public void onPrivateMessage(final PrivateMessageEvent event) {
        butt.getIrcMessageReceiver().handlePrivateMessage(event);
    }

}

package net.alureon.ircbutt.listener;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

/**
 * Created by alureon on 4/30/15.
 */
public class UserJoinListener extends ListenerAdapter {

    @Override
    public void onJoin(JoinEvent event) {
        if (event.getUser().isVerified()) {
            switch (event.getUser().getNick()) {
                case "[alureon]":
                case "nyherba":
                case "ebolahats":
                    event.getChannel().send().op(event.getUser());
                default:
                    break;
            }
        }
    }
}

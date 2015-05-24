package net.alureon.ircbutt.handler.command.karma;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alureon on 5/12/15.
 */

public class KarmaHandler {


    IRCbutt butt;
    String[] karmaEndings = { "--;", "++;", "++", "--" };
    final static Logger log = LoggerFactory.getLogger(KarmaHandler.class);


    public KarmaHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleKarma(BotResponse response, User user, String message) {
        if (message.contains(" ")) { return; }
        for (String x : karmaEndings) {
            if (message.endsWith(x)) {
                Karma karma = new Karma();
                karma.setType(getKarmaType(x));
                karma.setItem(message.replace(x, "").trim());
                switch (karma.getType()) {
                    case DECREMENT:
                        butt.getKarmaTable().decrementKarma(karma, user, response);
                        break;
                    case INCREMENT:
                        butt.getKarmaTable().incrementKarma(karma, user, response);
                }
            }
        }
    }

    public void getKarma(BotResponse response, User user, String message) {
        Integer karma = butt.getKarmaTable().getKarmaLevel(message);
        if (karma != null) {
            response.highlightChat(user, message + " has a karma level of " + karma);
        } else {
            response.privateMessage(user, "no karma record found for " + message);
        }
    }

    public KarmaType getKarmaType(String x) {
        switch (x) {
            case "++":
            case "++;":
                return KarmaType.INCREMENT;
            case "--":
            case "--;":
                return KarmaType.DECREMENT;
            default:
                log.error("Unhandled Karma Type: " + x);
                return null;
        }
    }


}

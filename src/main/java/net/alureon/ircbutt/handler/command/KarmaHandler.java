package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.Karma;
import net.alureon.ircbutt.KarmaType;
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

    public void handleKarma(BotResponse response, String message) {
        for (String x : karmaEndings) {
            if (message.endsWith(x)) {
                Karma karma = new Karma();
                karma.setType(getKarmaType(x));
                karma.setItem(message.replace(x, "").trim());
                if (!butt.getKarmaTable().itemExists(karma.getItem())) {

                } else {
                    //todo increment/decrement karma
                }
            }
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

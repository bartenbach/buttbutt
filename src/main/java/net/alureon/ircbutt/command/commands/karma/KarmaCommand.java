package net.alureon.ircbutt.command.commands.karma;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.User;

public class KarmaCommand {


    private static String[] karmaEndings = {"--;", "++;", "++", "--"};
    private final static Logger log = LogManager.getLogger(KarmaCommand.class);


    public static void handleKarma(IRCbutt butt, BotResponse response, User user, String message) {
        if (message.contains(" ")) {
            return;
        }
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

    public static void getKarma(IRCbutt butt, BotResponse response, User user, String message) {
        Integer karma = butt.getKarmaTable().getKarmaLevel(message);
        if (karma != null) {
            response.highlightChat(user, message + " has a karma level of " + karma);
        } else {
            response.privateMessage(user, "no karma record found for " + message);
        }
    }

    private static KarmaType getKarmaType(String x) {
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

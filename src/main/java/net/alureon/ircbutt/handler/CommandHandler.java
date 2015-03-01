package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.libmath.Trigonometry;
import net.alureon.ircbutt.util.GoogleResults;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(CommandHandler.class);


    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }


    public BotResponse handleCommand(MessageEvent event, String[] cmd, BotResponse response) {
        /* For the sake of clearer code, let's just set these immediately */
        User user = event.getUser();
        Channel channel = event.getChannel();
        String nick = user.getNick();

        /* if it's prefixed with a tilde it's a knowledge request */
        if (cmd[0].startsWith("~")) {
            return butt.getKnowledgeHandler().handleKnowledge(response, cmd, channel, user, nick);
        }

        /* remove the '!' from the command */
        cmd[0] = cmd[0].replaceFirst("!", "");

        /* switch of main bot commands */
        switch (cmd[0]) {
            case "rq":
            case "grab":
            case "q":
            case "qinfo":
            case "qsay":
            case "qsearch":
            case "qfind":
                return butt.getQuoteGrabHandler().handleQuoteGrabs(response, cmd, channel, user, nick);
            case "learn":
            case "forget":
            case "fact":
                return butt.getKnowledgeHandler().handleKnowledge(response, cmd, channel, user, nick);
            case "echo":

        }

        /*  Other functions, and odds and ends that are easier hard-coded than adding to a database.. */
        switch (cmd[0]) {
            case "bot":
                response.me("is a bot!");
                break;
            case "g":
                if (cmd.length > 1) {
                    // Todo strip html tags  - Testing
                    GoogleResults results = GoogleSearch.getMyGoogHoles(StringUtils.getArgs(cmd));
                    String title = results.getResponseData().getResults().get(0).getTitle().replaceAll("\\<.*?\\>", "");
                    String url = results.getResponseData().getResults().get(0).getUrl();
                    response.chat(title, url);
                }
                break;
            case "yt":
                // todo this is a joke.
                String link = "http://www.youtube.com/results?search_query=" + StringUtils.concatenateUrlArgs(cmd);
                response.chat(link);
                break;
            case "slap":
                if (cmd.length == 1) {
                    response.me("slaps " + nick + " with a large trout");
                } else {
                    response.me("slaps " +StringUtils.getArgs(cmd)+ " with a large trout");
                }
                break;
            case "version":
                response.chat(butt.getProgramName() + " " + butt.getProgramVersion());
                break;
            case "dice":
            case "roll":
                response = butt.getDiceHandler().handleDice(response, event.getChannel().getUsers());
                break;
            case "bloat":
                if (cmd.length > 1) {
                    response.chat(StringUtils.getArgs(cmd) + " is bloat.");
                }
                break;
            case "gn":
                response.chat("Good night to all from " + nick + "!");
                break;
            case "gm":
                response.chat("Good morning to all from " + nick + "!");
                break;
            case "source":
                response.chat(butt.getSourceRepository());
                break;
            case "dance":
                response.me("does the robot");
                break;
            case "random": {
                int random = (int) (Math.random() * 1000000);
                response.chat(String.valueOf(random));
                break;
            }
            case "drink":
                if (cmd.length < 2) {
                    response.chat("Have a drink, " + nick);
                } else {
                    response.chat("Have a drink, " + StringUtils.getArgs(cmd));
                }
                break;
            case "sin":  // todo this doesn't work for shit lol
                response.chat(Trigonometry.getSin(cmd[1]));
                break;
            case "check":
                butt.getCheckHandler().handleCheck(response, StringUtils.getArgs(cmd));
                break;
            case "define": //todo impossible
                break;
            case "weather":
                butt.getWeatherHandler().getFuckingWeather(response, cmd[1]);
                break;
        }
        return response;
    }
}

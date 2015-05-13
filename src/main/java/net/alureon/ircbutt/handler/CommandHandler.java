package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.libmath.Trigonometry;
import net.alureon.ircbutt.util.ButtMath;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(CommandHandler.class);


    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }


    public BotResponse handleCommand(GenericMessageEvent event, String[] cmd, BotResponse response) {
        /* For the sake of clearer code, let's just set these immediately */
        User user = event.getUser();
        String nick = user.getNick();

        /* if it's prefixed with a tilde it's a knowledge request */
        if (cmd[0].startsWith("~")) {
            return butt.getFactHandler().handleKnowledge(response, cmd, user, nick);
        }

        if (cmd[0].startsWith("%s/")) {
            // search and replace?
        }

        /* remove the '!' from the command */
        cmd[0] = cmd[0].replaceFirst("!", "");

        /* switch of main bot commands */
        switch (cmd[0]) {
            case "rq":
            case "grab":
            case "q":
            case "qinfo":
            case "qi":
            case "qsay":
            case "qsearch":
            case "qfind":
                return butt.getQuoteGrabHandler().handleQuoteGrabs(response, cmd, user, nick);
            case "learn":
            case "forget":
            case "fact":
            case "factinfo":
            case "factfind":
            case "factsearch":
            case "ffind":
            case "fsearch":
            case "finfo":
            case "ff":
            case "fi":
            case "fs":
                return butt.getFactHandler().handleKnowledge(response, cmd, user, nick);
            case "echo":
                butt.getEchoHandler().handleEcho(response, cmd);
        }

        /*  Other functions, and odds and ends that are easier hard-coded */
        // todo code creeping in here should be refactored to its respective class
        switch (cmd[0]) {
            case "g":
                butt.getGoogleSearchHandler().handleGoogleSearch(response, user, cmd);
                break;
            case "give":
                butt.getGiveHandler().handleGive(response, event, user, cmd);
                break;
            case "rot13":
                butt.getRot13Handler().handleRot13(response, StringUtils.getArgs(cmd));
                break;
            case "yt":
                butt.getYouTubeHandler().getYouTubeVideo(response, cmd);
                break;
            case "ud":
                butt.getUrbanDictionaryHandler().getDefinition(response, cmd);
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
                if (event instanceof MessageEvent) {
                    Channel channel = ((MessageEvent) event).getChannel();
                    response = butt.getDiceHandler().handleDice(response, channel.getUsers());
                }
                break;
            case "bloat":
                if (cmd.length > 1) {
                    response.chat(StringUtils.getArgs(cmd) + " is bloat.");
                }
                break;
            case "random": {
                response.chat(String.valueOf(ButtMath.getRandom()));
                break;
            }
            case "sin":  // todo this doesn't work for shit lol
                response.chat(Trigonometry.getSin(cmd[1]));
                break;
            case "cos":
                response.chat(Trigonometry.getCos(cmd[1]));
                break;
            case "tan":
                response.chat(Trigonometry.getTan(cmd[1]));
                break;
            case "check":
                butt.getCheckHandler().handleCheck(response, StringUtils.getArgs(cmd));
                break;
            case "define":
                butt.getDefineHandler().handleDefine(response, cmd[1]);
                break;
            case "weather":
                butt.getWeatherHandler().getFuckingWeather(response, cmd[1]);
                break;
            case "invite": //todo make him join
                butt.getInviteHandler().handleInvite(StringUtils.getArgs(cmd).split(" "));
                break;
            case "more":
                butt.getMoreHandler().handleMore(response);
                break;
        }
        if (response.getIntention() == null) {
            response.noResponse();
        }
        return response;
    }
}

package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.handler.command.*;
import net.alureon.ircbutt.handler.command.karma.KarmaHandler;
import net.alureon.ircbutt.libmath.Eval;
import net.alureon.ircbutt.libmath.MathLib;
import net.alureon.ircbutt.util.ButtMath;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class CommandHandler {


    private IRCbutt butt;


    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }


    // advice from Mandl:  /notice not query
    //                     parse formatting characters
    //                     .trim() ruining formatting


    public void handleCommand(GenericMessageEvent event, String[] cmd, BotResponse response) {
        /* For the sake of clearer code, let's just set these immediately */
        User user = event.getUser();
        String nick = user.getNick();

/*        for (String x : cmd) {
            if (x.equals(">>") || x.equals(">")) {
                //todo redirection handler
            }
        }*/

        /* if it's prefixed with a tilde it's a fact request */
        if (cmd[0].startsWith("~")) {
            butt.getFactHandler().handleKnowledge(response, cmd, user, nick);
            return;
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
            case "qf":
                butt.getQuoteGrabHandler().handleQuoteGrabs(response, cmd, user, nick);
                break;
            case "learn":
            case "append":
            case "forget":
            case "fact":
            case "factinfo":
            case "factfind":
            case "factsearch":
            case "ffind":
            case "fsearch":
            case "finfo":
            case "factbyid":
            case "factid":
            case "fid":
            case "ff":
            case "fi":
            case "fs":
                butt.getFactHandler().handleKnowledge(response, cmd, user, nick);
                break;
            case "echo":
                EchoHandler.handleEcho(butt, response, StringUtils.getArgs(cmd).split(" "), user.getNick());
                break;
            case "g":
                GoogleSearchHandler.handleGoogleSearch(butt, response, user, cmd);
                break;
            case "give":
                GiveHandler.handleGive(butt, response, event, user, cmd);
                break;
            case "rot13":
            case "rot":
                Rot13Handler.handleRot13(response, StringUtils.getArgs(cmd));
                break;
            case "yt":
                YouTubeHandler.getYouTubeVideo(butt, response, cmd);
                break;
            case "ud":
                UrbanDictionaryHandler.getDefinition(butt, response, cmd);
                break;
            case "version":
                response.chat(butt.getProgramVersion());
                break;
            case "dice":
                DiceHandler.handleDice(event, response);
                break;
            case "random":
                response.chat(String.valueOf(ButtMath.getRandom()));
                break;
            case "sqrt":
            case "pow":
                MathLib.handleMath(response, cmd);
                break;
            case "check":
                CheckHandler.handleCheck(response, StringUtils.getArgs(cmd));
                break;
            case "define":
                DefineHandler.handleDefine(butt, response, cmd[1]);
                break;
            case "invite":
                InviteHandler.handleInvite(butt, StringUtils.getArgs(cmd).split(" "));
                break;
            case "more":
                butt.getMoreHandler().handleMore(response);
                break;
            case "wr":
                WakeRoomHandler.handleWakeRoom(response);
                break;
            case "eval":
                Eval.eval(response, StringUtils.getArgs(cmd));
                break;
            case "karma":
                KarmaHandler.getKarma(butt, response, user, StringUtils.getArgs(cmd));
                break;
            case "coin":
                CoinHandler.handleCoin(response);
                break;
            case "gi":
                GoogleImageSearchHandler.handleGoogleImageSearch(response, StringUtils.getArgs(cmd));
                break;
        }
        if (response.getIntention() == null) {
            response.noResponse();
        }
    }
}

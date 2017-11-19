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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {


    private IRCbutt butt;


    public CommandHandler(IRCbutt butt) {
        this.butt = butt;
    }

    void handleCommand(GenericMessageEvent event, String[] cmd, BotResponse response) {
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

        if (!cmd[0].equals("learn")) {
            String commandSubstituted = parseCommandSubstitutionAndVariables(butt, response, StringUtils.arrayToString(cmd), nick);
            String[] commandSubstitutedArray = commandSubstituted.split(" ");

            /* switch of main bot commands */
            String result;
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
                case "rqnouser":
                case "rqn":
                    butt.getQuoteGrabHandler().handleQuoteGrabs(response, commandSubstitutedArray, user, nick);
                    break;
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
                    butt.getFactHandler().handleKnowledge(response, commandSubstitutedArray, user, nick);
                    break;
                case "echo":
                    response.chat(StringUtils.getArgs(commandSubstitutedArray));
                    break;
                case "g":
                    GoogleSearchHandler.handleGoogleSearch(butt, response, commandSubstitutedArray);
                    break;
                case "give":
                    result = GiveHandler.handleGive(response, event, user, commandSubstitutedArray);
                    response.chat(result);
                    break;
                case "rot13":
                case "rot":
                    result = Rot13Handler.handleRot13(StringUtils.getArgs(commandSubstitutedArray));
                    response.chat(result);
                    break;
                case "yt":
                    YouTubeHandler.getYouTubeVideo(butt, response, commandSubstitutedArray);
                    break;
                case "ud":
                    UrbanDictionaryHandler.getDefinition(butt, response, commandSubstitutedArray);
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
                    MathLib.handleMath(response, commandSubstitutedArray);
                    break;
                case "check":
                    CheckHandler.handleCheck(response, StringUtils.getArgs(commandSubstitutedArray));
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
                case "8":
                case "8ball":
                    MagicEightBallHandler.handleMagicEightBall(response);
                    break;
                case "butt":
                case "buttify":
                    String buttified = butt.getButtReplaceHandler().buttFormat(StringUtils.getArgs(commandSubstitutedArray));
                    response.chat(buttified);
                    break;
                default:
                    break;
            }
            if (response.getIntention() == null) {
                response.noResponse();
            }
        } else {
            butt.getFactHandler().handleKnowledge(response, cmd, user, nick);
        }
    }

    static String parseCommandSubstitutionAndVariables(IRCbutt butt, BotResponse response, String input, String nick) {
        Pattern p = Pattern.compile("\\$\\([^)]*\\)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            String command = m.group().substring(2, m.group().length() - 1);
            BotResponse botResponse = new BotResponse(response.getEvent());
            butt.getCommandHandler().handleCommand(response.getEvent(), command.split(" "), botResponse);
            input = input.replaceFirst(Pattern.quote(m.group()), botResponse.toString());
        }
        return input.replaceAll("\\$USER", nick);
    }

}

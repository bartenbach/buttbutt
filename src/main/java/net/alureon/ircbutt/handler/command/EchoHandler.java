package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoHandler {


    private final static Logger log = LoggerFactory.getLogger(EchoHandler.class);


    public static void handleEcho(IRCbutt butt, BotResponse response, String[] cmd, String nick) {
        String message = parseCommands(butt, response, StringUtils.arrayToString(cmd), nick);
        response.chat(message);
    }

    static String parseCommands(IRCbutt butt, BotResponse response, String input, String nick) {
        Pattern p = Pattern.compile("\\$\\([^)]*\\)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            String command = m.group().substring(2, m.group().length() - 1);
            BotResponse botResponse = new BotResponse(response.getEvent());
            butt.getCommandHandler().handleCommand(response.getEvent(), command.split(" "), botResponse);
            if (botResponse.toString() != null) {
                input = input.replaceFirst(Pattern.quote(m.group()), botResponse.toString());
            } else {
                botResponse.privateMessage(response.getRecipient(), "butt didnt get this part: " + m.group());
            }
        }
        return input.replaceAll("\\$USER", nick);
    }

}

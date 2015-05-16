package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alureon on 3/1/15.
 */

public class EchoHandler {

    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(EchoHandler.class);


    public EchoHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleEcho(BotResponse response, String[] cmd) {
        String message = parseMessage(response, cmd);
        response.chat(message);
    }

    public String parseMessage(BotResponse response, String[] cmd) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < cmd.length; i++) {
            if (cmd[i].contains("[")) {
                String command = parseCommand(cmd, i);
                String[] cmd2 = command.split(" ");
                i+=cmd2.length-1;
                log.debug("Command length: " + cmd2.length);
                    log.debug("Command to execute: " + command);
                    BotResponse commandResponse = new BotResponse(response.getMessageEvent());
                    commandResponse = butt.getCommandHandler().handleCommand(response.getMessageEvent(), cmd2, commandResponse);
                    if (commandResponse.toString() != null) {
                        log.debug("Command response: " + commandResponse.toString());
                        sb.append(commandResponse.toString()).append(" ");
                    } else {
                        response.privateMessage(response.getRecipient(), "invalid butt: " + command);
                    }
            } else {
                sb.append(cmd[i]).append(" ");
            }
        }
        return sb.toString();
    }

    public String parseCommand(String[] cmd, int j) {
        for (;j < cmd.length; j++ )
        if (cmd[j].contains("]")) {
            System.out.println("We have a command: " + cmd[j]);
            String command = cmd[j].substring(1,cmd[j].length()-1);
            System.out.println("Command is: " + command);
            return command;
        } else {
            StringBuilder s = new StringBuilder(cmd[j]);
            s.append(" ");
            for (int k=j+1;k < cmd.length;k++) {
                s.append(cmd[k]);
                if (cmd[k].contains("]")) {
                    System.out.println("We have a command: " + s.toString());  // strings i - j
                    String command = s.toString().trim();
                    return command.substring(1, command.length()-1);
                }
                s.append(" ");
            }
        }
        return null;
    }

}
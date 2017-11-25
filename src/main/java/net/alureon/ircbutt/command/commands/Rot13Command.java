package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles functions pertaining to Rot13.
 */
public final class Rot13Command implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        StringBuilder sb = new StringBuilder();
        for (char c : StringUtils.getArgs(cmd).toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char b = (char) (c + (byte) 13);
                if (b > 'Z') {
                    b -= 26;
                }
                sb.append(b);
            } else if (c >= 'a' && c <= 'z') {
                char b = (char) (c + (byte) 13);
                if (b > 'z') {
                    b -= 26;
                }
                sb.append(b);
            } else {
                sb.append(c);
            }
        }
        return new BotResponse(BotIntention.CHAT, null, sb.toString());
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return (ArrayList<String>) Arrays.asList("rot", "rot13");
    }
}

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

    /**
     * A constant holding the number of letters in the English alphabet.
     */
    private static final int LETTERS_IN_ALPHABET = 26;
    /**
     * A constant holding the number of positions to move for Rot13 encryption.
     */
    private static final int ROT13_SHIFT = 13;

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        StringBuilder sb = new StringBuilder();
        for (char c : StringUtils.getArgs(cmd).toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char b = (char) (c + (byte) ROT13_SHIFT);
                if (b > 'Z') {
                    b -= LETTERS_IN_ALPHABET;
                }
                sb.append(b);
            } else if (c >= 'a' && c <= 'z') {
                char b = (char) (c + (byte) ROT13_SHIFT);
                if (b > 'z') {
                    b -= LETTERS_IN_ALPHABET;
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
        return new ArrayList<>(Arrays.asList("rot", "rot13"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return true;
    }
}

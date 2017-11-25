package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.MathUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Flips a coin and returns the result based on random number generation.
 */
public final class CoinCommand implements Command {

    /**
     * The maximum number to use in random number generation.  Any number
     * higher than the number defined for COIN_MATH_HEADS will be tails.
     */
    private static final int COIN_MATH_MAX = 100;
    /**
     * Any number between COIN_MATH_HEADS (this number) and COIN_MATH_PENIS
     * will result in the coin flip being heads.
     */
    private static final int COIN_MATH_HEADS = 52;
    /**
     * Any number LESS THAN this will result in the coin landing on "penis".
     * How, we're not quite sure.  Maybe it landed on its side or something.
     */
    private static final int COIN_MATH_PENIS = 2;

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        int random = MathUtils.getRandom(0, COIN_MATH_MAX);
        String result;
        if (random < COIN_MATH_PENIS) {
            result = "penis";
        } else if (random < COIN_MATH_HEADS) {
            result = "heads";
        } else {
            result = "tails";
        }
        return new BotResponse(BotIntention.CHAT, null, result);
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("coin"));
    }
}

package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.MathUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Allows a user to ask a question to the Magic 8 Ball.
 */
public final class MagicEightBallCommand implements Command {

    /**
     * An ArrayList of every possible response the magic eight ball can have.  Yes, these are the actual responses
     * that a real magic 8 ball toy would give you.  All of them.
     */
    private static final ArrayList<String> MAGIC_EIGHT_BALL_RESPONSES = new ArrayList<>(Arrays.asList("It is certain",
            "It is decidedly so", "Without a doubt", "Yes, definitely", "You may rely on it", "As I see it, yes",
            "Outlook good", "Yes", "Signs point to yes", "Reply hazy, try again", "Ask again later",
            "Better not tell you now", "Cannot predict now", "Concentrate and ask again", "Don't count on it",
            "My reply is no", "My sources say no", "Outlook not so good", "Very doubtful", "Most likely"));

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        int random = MathUtils.getRandom(0, MAGIC_EIGHT_BALL_RESPONSES.size());
        return new BotResponse(BotIntention.CHAT, null, MAGIC_EIGHT_BALL_RESPONSES.get(random));
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("8", "8ball"));
    }
}

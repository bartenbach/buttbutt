package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains the methods necessary to work with JEval.
 */
public final class EvalCommand implements Command {

    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * Replaces any instance of the letter 'e' with the value of Math.E.
     * @param x The string to perform replacement in.
     * @return The string with 'e' replaced.
     */
    private static String replaceE(final String x) {
        return x.replaceAll("e", String.valueOf(Math.E));
    }

    /**
     * Returns all occurrences of the word 'pi' or 'π' with the value of pi.
     * @param x The string to perform replacement in.
     * @return The string with pi replaced with the constant.
     */
    private static String replacePi(final String x) {
        return x.replaceAll("pi", String.valueOf(Math.PI)).replaceAll("π", String.valueOf(Math.PI));
    }

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        BotResponse response;
        Evaluator evaluator = new Evaluator();
        String x = replacePi(StringUtils.getArgs(cmd));
        String y = replaceE(x);
        try {
            response = new BotResponse(BotIntention.CHAT, null, evaluator.evaluate(y));
        } catch (EvaluationException e) {
            response = new BotResponse(BotIntention.CHAT, null, "error evaluating expression: " + e.getMessage());
            log.error("Evaluation Exception ", e);
        }
        return response;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return (ArrayList<String>) Collections.singletonList("eval");
    }
}

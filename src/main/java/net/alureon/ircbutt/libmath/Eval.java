package net.alureon.ircbutt.libmath;

import net.alureon.ircbutt.BotResponse;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains the methods necessary to work with JEval.
 */
public final class Eval {

    /**
     * Prevent instantiation.
     */
    private Eval() {

    }

    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * Evaluates an expression using JEval.  The function first calls
     * replacePi() and replaceE() to replace them to their respective
     * constants.
     * @param response The BotResponse object
     * @param eval The string to evaluate
     */
    public static void eval(final BotResponse response, final String eval) {
        Evaluator evaluator = new Evaluator();
        String x = replacePi(eval);
        String y = replaceE(x);
        try {
            response.chat(evaluator.evaluate(y));
        } catch (EvaluationException e) {
            response.chat(e.getMessage());
            log.error("Evaluation Exception ", e);
        }
    }

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

}

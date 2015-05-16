package net.alureon.ircbutt.libmath;

import net.alureon.ircbutt.BotResponse;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alureon on 5/15/15.
 */

public class Eval {


    final static Logger log = LoggerFactory.getLogger(Eval.class);


    public static void eval(BotResponse response, String eval) {
        Evaluator evaluator = new Evaluator();
        try {
            response.chat(evaluator.evaluate(eval));
        } catch (EvaluationException e) {
            response.chat(e.getMessage());
            log.error("Evaluation Exception ", e);
        }
    }

}

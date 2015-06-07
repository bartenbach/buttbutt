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
        String x = replacePi(eval);
        String y = replaceE(x);
        try {
            response.chat(evaluator.evaluate(y));
        } catch (EvaluationException e) {
            response.chat(e.getMessage());
            log.error("Evaluation Exception ", e);
        }
    }

    private static String replaceE(String x) {
        if (x.contains("e"))
            x = x.replaceAll("e", String.valueOf(Math.E));
        return x;
    }

    private static String replacePi(String x) {
        if (x.contains("pi") || x.contains("π"))
            x = x.replaceAll("pi", String.valueOf(Math.PI)).replaceAll("π", String.valueOf(Math.PI));
        return x;
    }

    /*private static String fixExponents(String x) {
        if (x.contains("^")) {
            String[] exp  = x.split(" ");
            //fixme  this would suck to code...have to check for all mathematical operators on both sides
            //fixme   also parenthesis like 2-5^(5*4)+7
        }
    }*/

}

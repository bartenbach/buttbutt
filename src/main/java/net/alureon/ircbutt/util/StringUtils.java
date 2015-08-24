package net.alureon.ircbutt.util;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils {

    public static String arrayToString(String[] args) {
        return String.join(" ", args);
    }

    public static String getArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            String x = args[i];
            sb.append(x);
            if (i + 1 != args.length) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String getArgsOverOne(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            String x = args[i];
            sb.append(x);
            if (i + 1 != args.length) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String unescapeHtml(String x) {
        return x.replaceAll("&nbsp;", " ");
    }


}

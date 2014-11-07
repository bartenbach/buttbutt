package net.alureon.ircbutt.util;

public class StringUtils {

    public static String getArgs(String[] args) {
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.isEmpty()) {
                sb.append(x).append(" ");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String concatenateArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.isEmpty()) {
                sb.append(x).append(" ");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String getArgsOverOne(String[] args) {
        args[0] = "";
        args[1] = "";
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.isEmpty()) {
                sb.append(x).append(" ");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String concatenateUrlArgs(String[] args) {
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.isEmpty()) {
                sb.append(x).append("+");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}

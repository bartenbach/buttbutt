package net.alureon.ircbutt.util;

public class StringUtils {

    public static String arrayToString(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.isEmpty()) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static String getArgs(String[] args) {
        args[0] = "";
        StringBuilder sb = new StringBuilder();
        for (String x : args) {
            if (!x.isEmpty()) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString().trim();
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
        return sb.toString().trim();
    }

    public static String getTrimmedString(String x) {
        return "";
    }

}

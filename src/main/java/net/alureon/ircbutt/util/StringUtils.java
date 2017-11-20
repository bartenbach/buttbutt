package net.alureon.ircbutt.util;

/**
 * StringUtils provides many convenience methods for working with String arrays and String objects.
 */
public final class StringUtils {

    /**
     * This constructor is private to prevent object creation for a Utility class.
     */
    private StringUtils() {

    }

    /**
     * @param args - The string array to convert to a String
     * @return - The array converted to a String
     */
    public static String arrayToString(final String[] args) {
        return String.join(" ", args).trim();
    }

    public static String getArgs(final String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            String x = args[i];
            sb.append(x);
            if (i + 1 != args.length) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static String getArgsOverOne(final String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            String x = args[i];
            sb.append(x);
            if (i + 1 != args.length) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

}

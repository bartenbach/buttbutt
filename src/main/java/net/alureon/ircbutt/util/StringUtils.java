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

    /**
     * Takes an array and returns everything except the first element - the arguments, as a String object.
     * @param command The entire command array, split by whitespace.
     * @return String of just the arguments.
     */
    public static String getArgs(final String[] command) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < command.length; i++) {
            String x = command[i];
            sb.append(x);
            if (i + 1 != command.length) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

     /**
     * Takes an array and returns everything except the first element - the arguments, as a String object.
     * @param command The entire command array, split by whitespace.
     * @return String of just the arguments.
     */
    public static String[] getArgsArray(final String[] command) {
        String args = getArgs(command);
        return args.split("\\s");
    }

    /**
     * Takes an array and returns everything except the first and second elements, as a String object.
     * @param command The entire command array, split by whitespace.
     * @return String of just the arguments, minus the first one.
     */
    public static String getArgsOverOne(final String[] command) {
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < command.length; i++) {
            String x = command[i];
            sb.append(x);
            if (i + 1 != command.length) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

}

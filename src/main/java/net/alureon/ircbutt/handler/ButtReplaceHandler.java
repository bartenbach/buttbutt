package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.MathUtils;

/**
 * Provides the functionality for randomly 'buttifying' sentences in the IRC channel.
 */
public final class ButtReplaceHandler {


    /**
     * The instance of IRCbutt for getting configuration file values.
     */
    private IRCbutt butt;
    /**
     * When doing random number generation, this is the maximum number that can be generated.
     */
    private static final int BUTT_MATH_MAX = 100;
    /**
     * If the randomly generated number is less than this, 'butt' will be returned.  If greater, 'butts' will
     * be returned.
     */
    private static final int BUTT_MATH_TRIGGER = 80;
    /**
     * I have no idea what this does anymore.
     */
    private static final double BUTT_FORMAT_MAGIC_NUMBER = 8.0;


    /**
     * The constructor for the class.
     * @param butt The instance of IRCbutt for getting configuration file values.
     */
    public ButtReplaceHandler(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * Attempts to buttify the passed message.
     * @param message The message to buttify.
     * @return The buttified message.
     */
    String buttFormat(final String message) {
        String[] split = message.split(" ");
        replaceButt(split);
        int x = (int) Math.ceil(split.length / BUTT_FORMAT_MAGIC_NUMBER);
        for (int i = 1; i < x; i++) {
            attemptReplace(split);
        }
        StringBuilder sb = new StringBuilder();
        for (String y : split) {
            if (!y.startsWith(butt.getYamlConfigurationFile().getBotName())) {
                sb.append(y).append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Attemps to randomly replace a word in the array with 'butt' or 'butts'.
     * @param split The message from the user split on whitespace.
     */
    private void attemptReplace(final String[] split) {
        boolean success = replaceButt(split);
        if (!success) {
            buttRetry(split);
        }
    }

    /**
     * Attempts to randomly replace a word in the array with 'butt' or 'butts'.
     * @param split The message from the user split on whitespace.
     * @return True if it was able to make a replacement, otherwise false.
     */
    private boolean replaceButt(final String[] split) {
        int replace = (int) (Math.random() * split.length);
        if (!isAWordWeDontReplace(split[replace])) {
            if (split[replace].equalsIgnoreCase(butt.getYamlConfigurationFile().getBotName())) {
                split[replace] = randomize();
                return true;
            }

            StringBuilder sb = new StringBuilder(randomize());
            String suffix = "";
            if (split[replace].endsWith("?")) {
                suffix = "?";
            } else if (split[replace].endsWith("ed")) {
                suffix = "ed";
            } else if (split[replace].endsWith("ing")) {
                suffix = "ing";
            } else if (split[replace].endsWith("ify")) {
                suffix = "ify";
            } else if (split[replace].endsWith("ly")) {
                suffix = "ly";
            } else if (split[replace].endsWith("er")) {
                suffix = "er";
            } else if (split[replace].endsWith("'s")) {
                suffix = "'s";
            } else if (split[replace].endsWith("!")) {
                suffix = "!";
            } else if (split[replace].endsWith(".")) {
                suffix = ".";
            } else if (split[replace].endsWith("()")) {
                suffix = "()";
            } else if (split[replace].endsWith(")")) {
                suffix = ")";
            } else if (split[replace].endsWith(");")) {
                suffix = ");";
            } else if (split[replace].endsWith(":")) {
                suffix = ":";
            } else if (split[replace].endsWith(";")) {
                suffix = ";";
            } else if (split[replace].endsWith("\"")) {
                suffix = "\"";
            } else if (split[replace].endsWith("'")) {
                suffix = "'";
            } else if (split[replace].endsWith("*")) {
                suffix = "*";
            } else if (split[replace].endsWith("]")) {
                suffix = "]";
            }
            sb.append(suffix);

            String prefix = "";
            if (split[replace].startsWith("#")) {
                prefix = "#";
            } else if (split[replace].startsWith("/")) {
                prefix = "/";
            } else if (split[replace].startsWith("'")) {
                prefix = "'";
            } else if (split[replace].startsWith("\"")) {
                prefix = "\"";
            } else if (split[replace].startsWith("$")) {
                prefix = "$";
            } else if (split[replace].startsWith("%")) {
                prefix = "%";
            } else if (split[replace].startsWith("@")) {
                prefix = "@";
            } else if (split[replace].startsWith("^")) {
                prefix = "^";
            } else if (split[replace].startsWith("*")) {
                prefix = "*";
            } else if (split[replace].startsWith("!")) {
                prefix = "!";
            } else if (split[replace].startsWith("&")) {
                prefix = "&";
            } else if (split[replace].startsWith("('")) {
                prefix = "('";
            } else if (split[replace].startsWith(".(")) {
                prefix = ".(";
            } else if (split[replace].startsWith("(")) {
                prefix = "(";
            } else if (split[replace].startsWith("+")) {
                prefix = "+";
            } else if (split[replace].startsWith("+=")) {
                prefix = "+=";
            } else if (split[replace].startsWith("`")) {
                prefix = "`";
            } else if (split[replace].startsWith("~")) {
                prefix = "~";
            } else if (split[replace].startsWith("|")) {
                prefix = "|";
            } else if (split[replace].startsWith("[")) {
                prefix = "[";
            } else if (split[replace].startsWith("{")) {
                prefix = "{";
            } else if (split[replace].startsWith("<")) {
                prefix = "<";
            } else if (split[replace].startsWith(".")) {
                prefix = ".";
            } else if (split[replace].startsWith("-")) {
                prefix = "-";
            } else if (split[replace].startsWith("_")) {
                prefix = "_";
            }
            sb.insert(0, prefix);


            split[replace] = sb.toString();
            return true;
        }
        return false;
    }

    /**
     * Returns either 'butt' or 'butts' based on a random number generator.
     * @return A String containing either 'butt' or 'butts' depending on random number generation.
     */
    private String randomize() {
        int random = MathUtils.getRandom(0, BUTT_MATH_MAX);
        if (random < BUTT_MATH_TRIGGER) {
            return "butt";
        }
        return "butts";
    }

    /**
     * Attempts to avoid replacing words using the most god awful method imaginable.
     * @param replace The word we wish to check for being blacklisted for replacement.
     * @return True if the word has been blacklisted for replacement.
     */
    private boolean isAWordWeDontReplace(final String replace) {
        return replace.equalsIgnoreCase("is") || replace.equalsIgnoreCase("a")
                || replace.equalsIgnoreCase("the") || replace.equalsIgnoreCase("i")
                || replace.equalsIgnoreCase("my") || replace.equalsIgnoreCase("to")
                || replace.equalsIgnoreCase("in") || replace.equalsIgnoreCase("of")
                || replace.equalsIgnoreCase("butt") || replace.equalsIgnoreCase("and")
                || replace.equalsIgnoreCase("or") || replace.equalsIgnoreCase("your")
                || replace.equalsIgnoreCase("her") || replace.equalsIgnoreCase("his")
                || replace.equalsIgnoreCase("was") || replace.equalsIgnoreCase("lol")
                || replace.equalsIgnoreCase("wtf") || replace.equalsIgnoreCase("tbh")
                || replace.equalsIgnoreCase("haha") || replace.equalsIgnoreCase("it's")
                || replace.equalsIgnoreCase("for") || replace.equalsIgnoreCase("has")
                || replace.equalsIgnoreCase("if") || replace.equalsIgnoreCase("are");
    }

    /**
     * A god awful method that attempts to use the same terrible code again.
     * @param split The original message split on whitespace.
     */
    private void buttRetry(final String[] split) {
        boolean success = replaceButt(split);
        if (!success) {
            replaceButt(split);
        }
    }

    /**
     * Returns whether or not the bot should randomly buttify a user's message in chat or not, based on a number
     * defined in the bot's configuration file.
     * @return True if it is time for the bot to randomly buttify a message in the IRC channel.
     */
    boolean isRandomResponseTime() {
        int random = MathUtils.getRandom(0, butt.getYamlConfigurationFile().getRandomResponseFrequency());
        return random == 0;
    }

}

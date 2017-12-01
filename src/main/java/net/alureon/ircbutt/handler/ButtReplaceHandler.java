package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.MathUtils;
import net.alureon.ircbutt.util.StringUtils;

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
     * How frequently to apply the word "butt" to the message.
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
    String buttifyMessage(final String message) {
        // split the message on whitespace
        String[] split = message.split(" ");
        // get the index of the word to replace
        int replaceIndex = (int) (Math.random() * split.length);
        split[replaceIndex] = getReplacementWord(split[replaceIndex]);
        /*int x = (int) Math.ceil(split.length / BUTT_FORMAT_MAGIC_NUMBER);
        for (int i = 1; i < x; i++) {
            buttify(split);
        }*/
        return StringUtils.arrayToString(split);
    }

    /**
     * Attempts to randomly replace a word in the array with 'butt' or 'butts'.
     * @param word The word that we are attempting to replace.
     * @return True if it was able to make a replacement, otherwise false.
     */
    private String getReplacementWord(final String word) {
        // check for words we don't want to replace
        if (!isAWordWeDontReplace(word)) {
            // randomize the replacement word
            StringBuilder sb = new StringBuilder(randomize());

            // see if the word has a weird suffix and mimic that
            char suffix = word.charAt(word.length() - 1);
            if (!isAlphaNumeric(suffix)) {
                sb.append(suffix);
            }

            // get the prefix of the word.
            char prefix = word.charAt(0);
            // if it's totally uppercase, uppercase it.
            if (isUppercase(word)) {
                return sb.toString().toUpperCase();
            // if it starts with something weird, mimic that.
            } else if (!isAlphaNumeric(prefix)) {
                sb.insert(0, prefix);
            // if it's capitalized, mimic that.
            } else if (isCapitalized(word)) {
                sb.replace(0, 1, "B");
                //sb.insert(0, Character.toUpperCase(word.charAt(0)));
            }
            return sb.toString();
        }
        return word;
    }

    /**
     * Returns true if the word is capitalized.
     * @param word The word to check.
     * @return True if the word is capitalized.
     */
    private boolean isCapitalized(final String word) {
        return Character.isUpperCase(word.charAt(0));
    }

    /**
     * Returns true if every character in the word is uppercase.
     * @param word The word to check.
     * @return True if every character is uppercase.
     */
    private boolean isUppercase(final String word) {
        for (Character c : word.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not the character is alphanumeric.
     * @param c The character to check.
     * @return True if the character is a digit or letter, otherwise false.
     */
    private boolean isAlphaNumeric(final char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c);
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
     * Returns whether or not the bot should randomly buttify a user's message in chat or not, based on a number
     * defined in the bot's configuration file.
     * @return True if it is time for the bot to randomly buttify a message in the IRC channel.
     */
    boolean isRandomResponseTime() {
        int random = MathUtils.getRandom(0, butt.getYamlConfigurationFile().getRandomResponseFrequency());
        return random == 0;
    }

}

package net.alureon.ircbutt.command.commands;

/**
 * Handles functions pertaining to Rot13.
 */
public final class Rot13Command {

    /**
     * Prevent instantiation.
     */
    private Rot13Command() {

    }

    /**
     * This function takes a String and performs a Rot13 encryption.
     * @param in The input string to encrypt or decrypt.
     * @return The Rot13 encrypted String.
     */
    public static String handleRot13(final String in) {
        StringBuilder sb = new StringBuilder();
        for (char c : in.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char b = (char) (c + (byte) 13);
                if (b > 'Z') {
                    b -= 26;
                }
                sb.append(b);
            } else if (c >= 'a' && c <= 'z') {
                char b = (char) (c + (byte) 13);
                if (b > 'z') {
                    b -= 26;
                }
                sb.append(b);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

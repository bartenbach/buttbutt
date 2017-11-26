package net.alureon.ircbutt.handler;

import java.util.HashMap;

/**
 * This class stores messages from chat, just like an IRC buffer would
 * in an IRC client.  The reason for this copy, is so that we can reference
 * what a user said earlier.
 */
public final class ChatStorage {

// TODO how big could this get?  This needs to purge old messages!
    /**
     * A HashMap mapping a user to their recent chat messages.
     */
    private HashMap<String, String> messageStore = new HashMap<>();


    /**
     * This function stores a message in the ChatStorage's hashmap.
     * The key is the user's nickname, the value being the message.
     * @param nick The nickname of the user who chatted.
     * @param message The message that was chatted.
     */
    void storeMessage(final String nick, final String message) {
        messageStore.put(nick, message);
    }

    /**
     * Returns true if the ChatStorage map contains a quote from the supplied nick.
     * @param nick The nickname to query the message store for.
     * @return True if the message store has a quote from the supplied nickname.
     */
    public boolean hasQuoteFrom(final String nick) {
        return messageStore.containsKey(nick);
    }

    /**
     * Retrieves the last quote from the user with the passed nickname.
     * @param nick The nickname to get a quote from.
     * @return The last message the user chatted in the channel.
     */
    public String getLastQuoteFrom(final String nick) {
        return messageStore.get(nick);
    }

}

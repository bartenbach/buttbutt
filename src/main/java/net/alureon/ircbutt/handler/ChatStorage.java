package net.alureon.ircbutt.handler;

import java.util.HashMap;

/**
 * This class stores messages from chat, just like an IRC buffer would
 * in an IRC client.  The reason for this copy, is so that we can reference
 * what a user said earlier.
 */
public class ChatStorage {

// TODO how big could this get?  This needs to purge old messages!
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

    public boolean hasQuoteFrom(String nick) {
        return messageStore.containsKey(nick);
    }

    public String getLastQuoteFrom(String nick) {
        return messageStore.get(nick);
    }

}

package net.alureon.ircbutt.handler;

import java.util.HashMap;

public class ChatLoggingManager {


    private HashMap<String,String> messageLog = new HashMap<String, String>();


    public void logMessage(String nick, String message) {
        messageLog.put(nick, message);
    }

    public boolean hasQuoteFrom(String nick) {
        return messageLog.containsKey(nick);
    }

    public String getLastQuoteFrom(String nick) {
        return messageLog.get(nick);
    }

}

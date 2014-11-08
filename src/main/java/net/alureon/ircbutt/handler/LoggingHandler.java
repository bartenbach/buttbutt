package net.alureon.ircbutt.handler;

import org.slf4j.impl.SimpleLogger;

public class LoggingHandler {

    public LoggingHandler() {
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[MMMM dd yyyy hh:mm a]");
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
    }

}

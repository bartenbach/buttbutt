package net.alureon.ircbutt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;

public class LoggingHandler {


    static Logger log;


    public LoggingHandler() {
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[MM/dd/yy HH:mm]");
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");
        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
        System.setProperty(SimpleLogger.WARN_LEVEL_STRING_KEY, "WARNING");
        /* If I log to file, SimpleLogger no longer logs to the console... */
        //System.setProperty(SimpleLogger.LOG_FILE_KEY, "bot.log");
        log = LoggerFactory.getLogger(LoggingHandler.class);
    }

    public static void logCurrentLogLevel() {
        if (log.isTraceEnabled()) {
            log.trace("Logging at TRACE");
        } else if (log.isDebugEnabled()) {
            log.debug("Logging at DEBUG");
        } else if (log.isInfoEnabled()) {
            log.info("Logging at INFO");
        } else if (log.isWarnEnabled()) {
            log.warn("Logging at WARNING");
        } else if (log.isErrorEnabled()) {
            log.error("Logging at ERROR");
        } else {
            System.out.println("WARNING: No logging levels enabled!");
        }
    }

}

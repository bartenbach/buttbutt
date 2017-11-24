package net.alureon.ircbutt.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  LoggingHandler handles utility functions related to file and system logging.
 */
public final class LoggingHandler {

    /**
     * The logger object for the class.
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * Private constructor to prevent instantiation.
     */
    private LoggingHandler() {

    }

    /**
     * Logs to console current level that logging is occurring at.
     */
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

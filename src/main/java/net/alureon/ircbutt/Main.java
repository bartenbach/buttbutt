package net.alureon.ircbutt;

import net.alureon.ircbutt.handler.LoggingHandler;

public class Main {

    public static void main(String[] args) {
        /* Parse command line arguments for log level */
        String loglevel = "INFO";
        if (args.length > 0) {
            switch (args[0]) {
                case "TRACE":
                case "DEBUG":
                case "INFO":
                case "WARNING":
                case "ERROR":
                    loglevel = args[0];
                    break;
                default:
                    loglevel = "INFO";
                    break;
            }
        }

       /*  This was driving me crazy.  This HAS to happen before essentially any logging because of how
        *  SimpleLogger works.  Once a Logger is created, the properties CAN NOT be changed!  This also means
        *  that I can't simply add a config option to change the logging level.  Open to ideas on ways to do this
        *  without using hardcoded values.  (Parsing config.yml before logging means no log messages related to I/O) */
        LoggingHandler loggingHandler = new LoggingHandler(loglevel);

        IRCbutt ircbutt = new IRCbutt();
        ircbutt.start();
    }

}

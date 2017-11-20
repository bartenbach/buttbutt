package net.alureon.ircbutt;

/**
 * Copyright Blake Bartenbach 2014-2017
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 **/

import net.alureon.ircbutt.handler.LoggingHandler;

public class Main {

    public static void main(String[] args) {
        // setting the loglevel like this appears to be impossible with log4j
        // however, I'm open to being proven wrong
        /*String loglevel = "INFO";
        if (args.length > 0) {
            switch (args[0].toUpperCase()) {
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
        */
//        new LoggingHandler(loglevel);
        new LoggingHandler();

        IRCbutt ircbutt = new IRCbutt();
        ircbutt.start();
    }

}

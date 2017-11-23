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

/**
 * Represents the entry point for the program.
 */
public final class Main {

    /**
     * Prevent instantiation.
     */
    private Main() {

    }

    /**
     * The main method of the program. This method creates a new IRCbutt
     * object, and starts it.  That's all.
     * @param args - String array of arguments to the program.
     */
    public static void main(final String[] args) {
        // setting the logging level here would be preferable, if possible.
        // it would be nice if this could be configured via either command line flags,
        // or through the configuration file.
        IRCbutt ircbutt = new IRCbutt();
        ircbutt.start();
    }

}

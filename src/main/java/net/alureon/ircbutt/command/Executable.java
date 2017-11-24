package net.alureon.ircbutt.command;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;

/**
 * Acts as an interface for all commands.
 */
public interface Executable {


    /**
     * The Executable interface attempts to standardize bot command classes.  The commands
     * will all be given the same parameters, and are signing a contract to return a BotResponse
     * object.
     *
     * @param butt     The instance of IRCbutt (for access to other classes).
     * @param response The BotResponseObject.
     * @param cmd      The command the user entered.
     * @return The BotResponse object.
     */
    BotResponse executeCommand(IRCbutt butt, BotResponse response, String[] cmd);

}

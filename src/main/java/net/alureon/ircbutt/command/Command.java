package net.alureon.ircbutt.command;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Acts as an interface for all commands.
 */
public interface Command {


    /**
     * The Command interface attempts to standardize bot command classes.  The commands
     * will all be given the same parameters, and are signing a contract to return a BotResponse
     * object.
     *
     * @param butt     The instance of IRCbutt (for access to other classes).
     * @param event    The message event from PircBotX.
     * @param cmd      The command the user entered.
     */
    BotResponse executeCommand(IRCbutt butt, GenericMessageEvent event, String[] cmd);

}

package net.alureon.ircbutt.command;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;

/**
 * An interface for all bot commands.
 */
public interface Command {

    /**
     * The Command interface standardizes bot command classes.  Any command is forced to implement
     * this interface to execute, and will all be given the same parameters.  They are signing a
     * contract to return a BotResponse object.
     *
     * @param butt     The instance of IRCbutt (for access to other classes).
     * @param event    The message event from PircBotX.
     * @param cmd      The command the user entered.
     */
    BotResponse executeCommand(IRCbutt butt, GenericMessageEvent event, String[] cmd);

    /**
     * Returns the Strings that fire this command.  This will be the command after '!'.
     * @return The command strings
     */
    ArrayList<String> getCommandAliases();

}

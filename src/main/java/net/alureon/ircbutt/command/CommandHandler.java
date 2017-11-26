package net.alureon.ircbutt.command;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.commands.fact.FactCommand;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main CommandHandler for the program.  Any and all commands are routed here, then executed.
 */
public final class CommandHandler {

    /**
     * The instance of the IRCbutt object.
     */
    private IRCbutt butt;
    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * A mapping of what command maps to what class.
     */
    private HashMap<String, Command> commandMap = new HashMap<>();

    /**
     * Constructor sets the field of the IRCbutt instance.
     *
     * @param butt The IRCbutt instance.
     */
    public CommandHandler(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * Gets all classes that implement the Command interface and loads them, and their associated
     * command aliases into the command map.
     */
    public void registerCommandClasses() {
        log.info("Registering Commands...");
        Reflections reflections = new Reflections("net.alureon.ircbutt.command");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> c : classes) {
            try {
                Class<? extends Command> commandClass = Class.forName(c.getName()).asSubclass(Command.class);
                Constructor<? extends Command> constructor = commandClass.getConstructor();
                Command command = constructor.newInstance();
                for (String alias : command.getCommandAliases()) {
                    commandMap.put(alias, command);
                    log.info("Registered command '" + alias + "' to " + command.getClass().getSimpleName());
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InstantiationException | InvocationTargetException e) {
                log.error("Failed to register command class: " + e.getMessage());
            }
        }
    }

    /**
     * The main function that handles all commands passed to the bot.
     *
     * @param event         The GenericMessageEvent received from the PircBotX API.
     * @param commandString The entire command the user has entered.
     * @return The bot's intended response in a BotResponse object.
     */
    public BotResponse handleCommand(final GenericMessageEvent event, final String commandString) {
        /* Split the command on whitespace */
        String[] cmd = commandString.split("\\s");

        /* if it's prefixed with a tilde it's a fact request */
        if (cmd[0].startsWith("~")) {
            return new FactCommand().executeCommand(butt, event, cmd);
        }

        /* remove the '!' from the command */
        cmd[0] = cmd[0].replaceFirst("!", "");

        /* Check command map and execute command */
        if (commandMap.containsKey(cmd[0])) {
            Command command = commandMap.get(cmd[0]);
            if (command.allowsCommandSubstitution()) {
                /* Perform command substitution */
                String commandSubstituted = parseCommandSubstitutionAndVariables(event, StringUtils.arrayToString(cmd));
                cmd = commandSubstituted.split(" ");
                log.debug("CommandSubstitutedArray: " + StringUtils.arrayToString(cmd));
            }
            return command.executeCommand(butt, event, cmd);
        } else {
            log.error("Ended up in command handler but command not registered! " + StringUtils.arrayToString(cmd));
            return new BotResponse(BotIntention.NO_REPLY, null, null);
        }
    }

    /**
     * This function performs command substitution before actually executing a command.  Any commands
     * with the variable $USER is replaced with the nick of the person giving the command.  Any commands
     * surrounded by $() are expanded to what their value would be if the command was executed, by this
     * function.
     *
     * @param event The event from PircBotX.
     * @param input The input from the user.
     * @return a string with all commands expanded to their values.
     */
    private String parseCommandSubstitutionAndVariables(final GenericMessageEvent event, final String input) {
        String result = input;
        Pattern p = Pattern.compile("\\$\\([^)].*\\)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            String command = m.group().substring(2, m.group().length() - 1);
            BotResponse response = handleCommand(event, command);
            result = input.replaceFirst(Pattern.quote(m.group()), response.getMessage());
        }
        log.debug("Parsed Command Substitution result: " + result);
        return result.replaceAll("\\$USER", event.getUser().getNick());
    }

}

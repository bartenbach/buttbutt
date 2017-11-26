package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.command.CommandHandler;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to hold tests for the CommandHandler.
 */
public final class CommandHandlerTest {

    /**
     * A quick and dirty manual test for registering commands.
     */
    @Test
    public void testRegisteringCommands() {
        new CommandHandler(null).registerCommandClasses();
    }

    /**
     * Another manual test, but for command substitution.
     */
    @Test
    public void testCommandSubstitution() {
        String input = "echo $(fact $(fact))";
        String result = input;
        Pattern p = Pattern.compile("\\$\\([^)].*\\)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            System.out.println("Group: " + m.group());
            String command = m.group().substring(2, m.group().length() - 1);
            System.out.println(command);
            //BotResponse response = handleCommand(event, command);
            //result = input.replaceFirst(Pattern.quote(m.group()), response.getMessage());
        }
        //log.debug("Parsed Command Substitution result: " + result);
        //return result.replaceAll("\\$USER", event.getUser().getNick());
    }

}

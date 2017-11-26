package net.alureon.ircbutt;

import net.alureon.ircbutt.command.*;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the main class to set up tests for the bot.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GiveCommandTest.class, EchoCommandTest.class, CoinCommandTest.class, Rot13CommandTest.class})
public final class IRCbuttTest {

    /**
     * The instance of the CommandHandler.
     */
    private static final CommandHandler COMMAND_HANDLER = new CommandHandler(null);
    /**
     * A flag indicating whether or not the commands have been registered.
     */
    private static boolean isSetUp = false;

    /**
     * Private constructor.
     */
    private IRCbuttTest() {
    }

    /**
     * Registers all commands for testing.
     */
    @BeforeClass
    public static void setUp() {
        if (!isSetUp) {
            System.out.println("Setting up tests...");
            COMMAND_HANDLER.registerCommandClasses();
            isSetUp = true;
        }
    }

    /**
     * Returns the test instance of the CommandHandler.
     * @return The test CommandHandler object.
     */
    public static CommandHandler getCommandHandler() {
        setUp();
        return COMMAND_HANDLER;
    }
}

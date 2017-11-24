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

import net.alureon.ircbutt.file.YAMLConfigurationFile;
import net.alureon.ircbutt.handler.*;
import net.alureon.ircbutt.command.CommandHandler;
import net.alureon.ircbutt.command.commands.fact.FactCommand;
import net.alureon.ircbutt.command.commands.MoreCommand;
import net.alureon.ircbutt.command.commands.quotegrabs.QuoteGrabCommand;
import net.alureon.ircbutt.command.commands.karma.KarmaTable;
import net.alureon.ircbutt.listener.ChatListener;
import net.alureon.ircbutt.listener.PrivateMessageListener;
import net.alureon.ircbutt.command.commands.fact.FactTable;
import net.alureon.ircbutt.command.commands.quotegrabs.QuoteGrabTable;
import net.alureon.ircbutt.logging.LoggingHandler;
import net.alureon.ircbutt.sql.SqlManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.managers.ListenerManager;
import org.pircbotx.hooks.managers.ThreadedListenerManager;

/**
 * The main class for IRCbutt.  This class instantiates the bot.
 */
public final class IRCbutt {

    /**
     * Grabs the current program version from the build.gradle file.
     */
    private final String programVersion = this.getClass().getPackage().getImplementationVersion();
    /**
     * Create a log4j logger.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * Instantiate the ButtReplaceHandler object.
     */
    private ButtReplaceHandler buttReplaceHandler = new ButtReplaceHandler(this);
    /**
     * Instantiate the ComamandHandler object.
     */
    private CommandHandler commandHandler = new CommandHandler(this);
    /**
     * Instantiate the YAMLConfigurationFile object.
     */
    private YAMLConfigurationFile yamlConfigurationFile = new YAMLConfigurationFile();
    /**
     * Instantiate the SqlManager object.
     */
    private SqlManager sqlManager = new SqlManager(this);
    /**
     * Instantiate the FactTable object.
     */
    private FactTable factTable = new FactTable(this);
    /**
     * Instantiate the QuoteGrabTable object.
     */
    private QuoteGrabTable quoteGrabTable = new QuoteGrabTable(this);
    /**
     * Instantiate the FactCommand object.
     */
    private FactCommand factCommand = new FactCommand(this);
    /**
     * Instantiates a ListenerManager object.
     */
    private ListenerManager listenerManager = new ThreadedListenerManager();
    /**
     * Instantiates a IrcMessageReceiver object.
     */
    private IrcMessageReceiver ircMessageReceiver = new IrcMessageReceiver(this);
    /**
     * Instantiates a ChatStorage object for logging the chat.
     */
    private ChatStorage chatStorage = new ChatStorage();
    /**
     * Instantiates a QuoteGrabCommand object for managing quotegrabs.
     */
    private QuoteGrabCommand quoteGrabCommand = new QuoteGrabCommand(this);
    /**
     * Instantiates a KarmaTable object for handling SQL-related Karma operations.
     */
    private KarmaTable karmaTable = new KarmaTable(this);
    /**
     * Instantiates a MoreCommand object to handle bot responses where there are many results.
     */
    private MoreCommand moreCommand = new MoreCommand();
    /**
     * Instantiates the PircBotX object, the API used for connecting to IRC.
     */
    private PircBotX pircBotX;


    /**
     * The Class Constructor creates the configuration file for the bot if it doesn't exist.
     * If it does, it parses it.  Then, it attempts to acquire an SQL connection.
     * This is bot instantiation.
     */
    public IRCbutt() {
        /* Create / parse yaml configuration file */
        yamlConfigurationFile.createConfigIfNotExists();
        yamlConfigurationFile.parseConfig();

        /* Connect to SQL database */
        sqlManager.connectToDatabase();
        sqlManager.createDatabaseIfNotExists();
        sqlManager.createTablesIfNotExists();
    }

    /**
     * This function actually starts the bot.
     */
    void start() {
        /* Log initiation and current logging level */
        log.info("Starting IRCButt version " + programVersion);
        LoggingHandler.logCurrentLogLevel();

        /* Add event listeners */
        listenerManager.addListener(new ChatListener(this));
        listenerManager.addListener(new PrivateMessageListener(this));

        /* Set the bot's configuration variables */
        Configuration configuration = new BotConfigurationHandler(this).getConfiguration();

        /* Create the bot with our configuration */
        this.pircBotX = new PircBotX(configuration);

        /* Start the bot */
        try {
            pircBotX.startBot();
        } catch (Exception ex) {  // several exceptions can be thrown here
            log.error("Unable to start bot.  StackTrace: ", ex);
        }
    }

    /**
     * Returns the ButtReplaceHandler.
     * @return ButtReplaceHandler
     */
    public ButtReplaceHandler getButtReplaceHandler() {
        return this.buttReplaceHandler;
    }

    /**
     * Returns the current version of the program.
     * @return String - current version of the program
     */
    public String getProgramVersion() {
        return this.programVersion;
    }

    /**
     * Returns the CommandHandler object.
     * @return CommandHandler
     */
    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    /**
     * Returns the YAMLConfigurationFile object.
     * @return YAMLConfigurationFile
     */
    public YAMLConfigurationFile getYamlConfigurationFile() {
        return this.yamlConfigurationFile;
    }

    /**
     * Returns the SqlManager object.
     * @return SQLManager
     */
    public SqlManager getSqlManager() {
        return this.sqlManager;
    }

    /**
     * Returns the FactTable object.
     * @return FactTable
     */
    public FactTable getFactTable() {
        return this.factTable;
    }

    /**
     * Returns the FactCommand object.
     * @return FactCommand
     */
    public FactCommand getFactCommand() {
        return this.factCommand;
    }

    /**
     * Returns the ListenerManager object.
     * @return ListenerManager
     */
    public ListenerManager getListenerManager() {
        return this.listenerManager;
    }

    /**
     * Returns the IrcMessageReceiver object.
     * @return IrcMessageReceiver
     */
    public IrcMessageReceiver getIrcMessageReceiver() {
        return this.ircMessageReceiver;
    }

    /**
     * Returns the ChatStorage object.
     * @return ChatStorage
     */
    public ChatStorage getChatStorage() {
        return this.chatStorage;
    }

    /**
     * Returns the QuoteGrabTable object.
     * @return QuoteGrabTable
     */
    public QuoteGrabTable getQuoteGrabTable() {
        return this.quoteGrabTable;
    }

    /**
     * Returns the QuoteGrabCommand object.
     * @return QuoteGrabCommand
     */
    public QuoteGrabCommand getQuoteGrabCommand() {
        return this.quoteGrabCommand;
    }

    /**
     * Returns the MoreCommand object.
     * @return MoreCommand
     */
    public MoreCommand getMoreCommand() {
        return this.moreCommand;
    }

    /**
     * Returns the PircBotX API object.
     * @return PircBotX
     */
    public PircBotX getPircBotX() {
        return this.pircBotX;
    }

    /**
     * Returns the KarmaTable object.
     * @return KarmaTable
     */
    public KarmaTable getKarmaTable() {
        return this.karmaTable;
    }
}

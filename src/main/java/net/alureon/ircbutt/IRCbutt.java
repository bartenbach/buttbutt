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
import net.alureon.ircbutt.handler.command.FactHandler;
import net.alureon.ircbutt.handler.command.MoreHandler;
import net.alureon.ircbutt.handler.command.QuoteGrabHandler;
import net.alureon.ircbutt.handler.command.karma.KarmaTable;
import net.alureon.ircbutt.listener.ChatListener;
import net.alureon.ircbutt.listener.PrivateMessageListener;
import net.alureon.ircbutt.sql.FactTable;
import net.alureon.ircbutt.sql.QuoteGrabTable;
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
     * Instantiate the BotChatHandler object.
     */
    private BotChatHandler botChatHandler = new BotChatHandler();
    /**
     * Instantiate the ButtReplaceHandler object.
     */
    private ButtReplaceHandler buttReplaceHandler = new ButtReplaceHandler(this);
    private CommandHandler commandHandler = new CommandHandler(this);
    private YAMLConfigurationFile yamlConfigurationFile = new YAMLConfigurationFile();
    private SqlManager sqlManager = new SqlManager(this);
    private FactTable factTable = new FactTable(this);
    private QuoteGrabTable quoteGrabTable = new QuoteGrabTable(this);
    private FactHandler factHandler = new FactHandler(this);
    private ListenerManager listenerManager = new ThreadedListenerManager();
    private MessageHandler messageHandler = new MessageHandler(this);
    private ChatLoggingManager chatLoggingManager = new ChatLoggingManager();
    private QuoteGrabHandler quoteGrabHandler = new QuoteGrabHandler(this);
    private KarmaTable karmaTable = new KarmaTable(this);
    private MoreHandler moreHandler = new MoreHandler();
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
        SqlManager.connectToDatabase();
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
     * Returns the BotChatHandler.
     * @return BotChatHandler
     */
    public BotChatHandler getBotChatHandler() {
        return this.botChatHandler;
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
     * Returns the FactHandler object.
     * @return FactHandler
     */
    public FactHandler getFactHandler() {
        return this.factHandler;
    }

    /**
     * Returns the ListenerManager object.
     * @return ListenerManager
     */
    public ListenerManager getListenerManager() {
        return this.listenerManager;
    }

    /**
     * Returns the MessageHandler object.
     * @return MessageHandler
     */
    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    /**
     * Returns the ChatLoggingManager object.
     * @return ChatLoggingManager
     */
    public ChatLoggingManager getChatLoggingManager() {
        return this.chatLoggingManager;
    }

    /**
     * Returns the QuoteGrabTable object.
     * @return QuoteGrabTable
     */
    public QuoteGrabTable getQuoteGrabTable() {
        return this.quoteGrabTable;
    }

    /**
     * Returns the QuoteGrabHandler object.
     * @return QuoteGrabHandler
     */
    public QuoteGrabHandler getQuoteGrabHandler() {
        return this.quoteGrabHandler;
    }

    /**
     * Returns the MoreHandler object.
     * @return MoreHandler
     */
    public MoreHandler getMoreHandler() {
        return this.moreHandler;
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

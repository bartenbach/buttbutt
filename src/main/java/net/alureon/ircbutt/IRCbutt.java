package net.alureon.ircbutt;

/**
    Copyright Blake Bartenbach 2014-2015
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
**/

import net.alureon.ircbutt.file.YAMLConfigurationFile;
import net.alureon.ircbutt.handler.*;
import net.alureon.ircbutt.handler.command.FactHandler;
import net.alureon.ircbutt.handler.command.GiveHandler;
import net.alureon.ircbutt.handler.command.MoreHandler;
import net.alureon.ircbutt.handler.command.QuoteGrabHandler;
import net.alureon.ircbutt.handler.command.karma.KarmaTable;
import net.alureon.ircbutt.listener.ChatListener;
import net.alureon.ircbutt.listener.PrivateMessageListener;
import net.alureon.ircbutt.sql.FactTable;
import net.alureon.ircbutt.sql.QuoteGrabTable;
import net.alureon.ircbutt.sql.SqlManager;
import net.alureon.ircbutt.util.IRCUtils;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.managers.ListenerManager;
import org.pircbotx.hooks.managers.ThreadedListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IRCbutt {


    /* Program-related constants */
    private final String programVersion = this.getClass().getPackage().getImplementationVersion();

    /* Logger */
    final static Logger log = LoggerFactory.getLogger(IRCbutt.class);

    /* Class instantiation */
    private BotChatHandler botChatHandler = new BotChatHandler();
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
    private IRCUtils ircUtils = new IRCUtils(this);
    private GiveHandler giveHandler = new GiveHandler(this);
    private KarmaTable karmaTable = new KarmaTable(this);
    private MoreHandler moreHandler = new MoreHandler();
    private PircBotX pircBotX;


    public IRCbutt () {
        /* Create / parse yaml configuration file */
        yamlConfigurationFile.createConfigIfNotExists();
        yamlConfigurationFile.parseConfig();

        /* Connect to SQL database */
        sqlManager.connectToDatabase();
        sqlManager.createTablesIfNotExists();
    }

    public void start() {
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

    public BotChatHandler getBotChatHandler() { return this.botChatHandler; }

    public ButtReplaceHandler getButtReplaceHandler() {
        return this.buttReplaceHandler;
    }

    public String getProgramVersion() {
        return this.programVersion;
    }

    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    public YAMLConfigurationFile getYamlConfigurationFile() {
        return this.yamlConfigurationFile;
    }

    public SqlManager getSqlManager() {
        return this.sqlManager;
    }

    public FactTable getFactTable() {
        return this.factTable;
    }

    public FactHandler getFactHandler() {
        return this.factHandler;
    }

    public ListenerManager getListenerManager() {
        return this.listenerManager;
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    public ChatLoggingManager getChatLoggingManager() {
        return this.chatLoggingManager;
    }

    public QuoteGrabTable getQuoteGrabTable() {
        return this.quoteGrabTable;
    }

    public QuoteGrabHandler getQuoteGrabHandler() { return this.quoteGrabHandler; }

    public IRCUtils getIrcUtils() { return this.ircUtils; }

    public GiveHandler getGiveHandler() { return this.giveHandler; }

    public MoreHandler getMoreHandler() { return this.moreHandler; }

    public PircBotX getPircBotX() { return this.pircBotX; }

    public KarmaTable getKarmaTable() { return this.karmaTable; }
}

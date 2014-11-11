package net.alureon.ircbutt;

/*
    Copyright Blake Bartenbach 2014
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
*/

import net.alureon.ircbutt.file.YAMLConfigurationFile;
import net.alureon.ircbutt.handler.*;
import net.alureon.ircbutt.listener.ChatListener;
import net.alureon.ircbutt.listener.PrivateMessageListener;
import net.alureon.ircbutt.sql.KnowledgeTable;
import net.alureon.ircbutt.sql.QuoteGrabTable;
import net.alureon.ircbutt.sql.SqlManager;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.managers.ListenerManager;
import org.pircbotx.hooks.managers.ThreadedListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IRCbutt {


    /* Program-related constants */
    private final String programName = this.getClass().getPackage().getImplementationTitle();
    private final String programVersion = this.getClass().getPackage().getImplementationVersion();
    private final String sourceRepository = "https://github.com/proxa/IRCbutt";

    /* Logger */
    final static Logger log = LoggerFactory.getLogger(IRCbutt.class);

    /* Class instantiation */
    private ButtNameResponseHandler buttNameResponseHandler = new ButtNameResponseHandler(this);
    private ButtChatHandler buttChatHandler = new ButtChatHandler();
    private ButtFormatHandler buttFormatHandler = new ButtFormatHandler();
    private CommandHandler commandHandler = new CommandHandler(this);
    private YAMLConfigurationFile yamlConfigurationFile = new YAMLConfigurationFile();
    private SqlManager sqlManager = new SqlManager(this);
    private KnowledgeTable knowledgeTable = new KnowledgeTable(this);
    private QuoteGrabTable quoteGrabTable = new QuoteGrabTable(this);
    private KnowledgeHandler knowledgeHandler = new KnowledgeHandler(this);
    private ListenerManager<PircBotX> listenerManager = new ThreadedListenerManager<PircBotX>();
    private MessageHandler messageHandler = new MessageHandler(this);


    public IRCbutt () {
        /* Log initiation and current logging level */
        log.info("Starting IRCButt version " + programVersion);
        LoggingHandler.logCurrentLogLevel();

        /* Create / parse yaml configuration file */
        yamlConfigurationFile.createConfigIfNotExists();
        yamlConfigurationFile.parseConfig();

        /* Connect to SQL database */
        sqlManager.connectToDatabase();
        sqlManager.createTablesIfNotExists();

        /* Add event listeners */
        listenerManager.addListener(new ChatListener(this));
        listenerManager.addListener(new PrivateMessageListener(this));

        /* Set the bot's configuration variables */
        Configuration<PircBotX> configuration = new BotConfigurationHandler(this).getConfiguration();

        /* Create the bot with our configuration */
        PircBotX bot = new PircBotX(configuration);

        /* Start the bot */
        try {
            bot.startBot();
        } catch (IOException | IrcException ex) {
            log.error("Unable to start bot.  StackTrace: ", ex);
        }
    }

    public ButtChatHandler getButtChatHandler() {
        return this.buttChatHandler;
    }

    public ButtNameResponseHandler getButtNameResponseHandler() {
        return this.buttNameResponseHandler;
    }

    public ButtFormatHandler getButtFormatHandler() {
        return this.buttFormatHandler;
    }

    public String getProgramName() {
        return this.programName;
    }

    public String getProgramVersion() {
        return this.programVersion;
    }

    public String getSourceRepository() {
        return this.sourceRepository;
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

    public KnowledgeTable getKnowledgeTable() {
        return this.knowledgeTable;
    }

    public KnowledgeHandler getKnowledgeHandler() {
        return this.knowledgeHandler;
    }

    public ListenerManager<PircBotX> getListenerManager() {
        return this.listenerManager;
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }

}

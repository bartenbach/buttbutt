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
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IRCbutt {


    /* Program-related constants */
    private final String programName = this.getClass().getPackage().getImplementationTitle();
    private final String programVersion = this.getClass().getPackage().getImplementationVersion();
    private final String sourceRepository = "https://github.com/proxa/IRCbutt";

    /* Class instantiation */
    private ButtNameResponseHandler buttNameResponseHandler = new ButtNameResponseHandler(this);
    private ButtChatHandler buttChatHandler = new ButtChatHandler();
    private ButtFormatHandler buttFormatHandler = new ButtFormatHandler();
    private CommandHandler commandHandler = new CommandHandler(this);
    private LoggingHandler loggingHandler = new LoggingHandler();

    /* Logger */
    public final static Logger log = LoggerFactory.getLogger(IRCbutt.class);


    public IRCbutt () {
        /* Create/parse configuration file */
        YAMLConfigurationFile yamlConfigurationFile = new YAMLConfigurationFile();

        /* Set the bot's configuration variables */
        Configuration<PircBotX> configuration = new Configuration.Builder<PircBotX>()
                .setName("buttbutt")
                .setLogin("buttbutt")
                .setServerHostname("irc.esper.net")
                .addAutoJoinChannel("#oatpaste")
                .setAutoReconnect(true)
                .setMessageDelay(10L)
                .setNickservPassword("you think i'd leave this in here?")
                .setVersion(programVersion)
                .addListener(new ChatListener(this))
                .buildConfiguration();

        /* Create the bot with our configuration */
        PircBotX bot = new PircBotX(configuration);

        /* Start the bot */
        try {
            bot.startBot();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (IrcException ex) {
            ex.printStackTrace();
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

}

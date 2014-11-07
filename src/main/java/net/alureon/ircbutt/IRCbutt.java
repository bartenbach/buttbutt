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

import net.alureon.ircbutt.handler.*;
import net.alureon.ircbutt.listener.ChatListener;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

public class IRCbutt {


    private ButtNameResponseHandler buttNameResponseHandler = new ButtNameResponseHandler(this);
    private ButtChatHandler buttChatHandler = new ButtChatHandler();
    private ButtFormatHandler buttFormatHandler = new ButtFormatHandler();
    private ChatListener chatListener = new ChatListener(this);


    public IRCbutt () {
        Configuration configuration = new Configuration.Builder()
                .setName("buttbutt")
                .setLogin("buttbutt")
                .setServerHostname("chat.freenode.net")
                .addAutoJoinChannel("#oatpaste")
                .addListener(chatListener)
                .buildConfiguration();

        PircBotX bot = new PircBotX(configuration);
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

}

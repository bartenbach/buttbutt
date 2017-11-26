package net.alureon.ircbutt.handler;

import com.google.common.base.Preconditions;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.commands.karma.KarmaCommand;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * The IrcMessageReceiver class handles all chat messages picked up by the PircBotX listener
 * defined in ChatListener and PrivateMessageListener.  It then decides what to do with
 * the message based on whether it is a fact request, a command, or just chatter.
 */
public final class IrcMessageReceiver {

    /**
     * The private instance of the IRCbutt class.
     */
    private IRCbutt butt;


    /**
     * Constructor sets the private instance of the IRCbutt class.
     *
     * @param butt The IRCbutt instance.
     */
    public IrcMessageReceiver(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * This function handles all incoming messages from IRC channels.  It decides what
     * to do with the message based on it's prefix, or suffix.
     *
     * @param event The MessageEvent object coming from PircBotX listener.
     */
    public void handleMessage(final MessageEvent event) {
        /* Handle a command */
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            BotResponse response = butt.getCommandHandler().handleCommand(event, event.getMessage());
            ResponseHandler.handleResponse(response, event);

        /* Handle karma */
        } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;")
                || event.getMessage().endsWith("--") || event.getMessage().endsWith("--;")) {
            new KarmaCommand().karmaListener(butt, event.getMessage());
        } else {
            /* Check for URL or troll them */
            Preconditions.checkNotNull(event.getUser(), "Attempted to store message of null user.");
            butt.getChatStorage().storeMessage(event.getUser().getNick(), event.getMessage());

            // don't troll URL's
            if (UrlTitleHandler.handleUrl(event.getChannel(), event.getMessage())) {
                return;
            }

            // buttify sentence
            final String buttFormat = butt.getButtReplaceHandler().buttFormat(event.getMessage()).trim();
            if (!buttFormat.equals(event.getMessage()) && buttFormat.contains(" ")) {
                IRCUtils.sendChannelMessage(event.getChannel(), buttFormat);
            }
        }
    }

    /**
     * This is the handler for private messages with the bot.
     *
     * @param event The PrivateMessageEvent from the PircBotX listener.
     */
    public void handlePrivateMessage(final PrivateMessageEvent event) {
        Preconditions.checkArgument(event.getUser() != null, "User was null");
        if (butt.getYamlConfigurationFile().getBotNoVerify() || event.getUser().isVerified()) {
            if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
                butt.getCommandHandler().handleCommand(event, event.getMessage());
                //ResponseHandler.handleResponse(response);
            } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;")
                    || event.getMessage().endsWith("--") || event.getMessage().endsWith("--;")) {
                new KarmaCommand().karmaListener(butt, event.getMessage());
            }
        }
    }

}

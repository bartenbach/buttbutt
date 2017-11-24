package net.alureon.ircbutt.handler;

import com.google.common.base.Preconditions;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.commands.karma.KarmaCommand;
import net.alureon.ircbutt.util.MathUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * The IrcMessageHandler class handles all chat messages picked up by the PircBotX listener
 * defined in ChatListener and PrivateMessageListener.  It then decides what to do with
 * the message based on whether it is a fact request, a command, or just chatter.
 */
public class IrcMessageHandler {

    /**
     * The private instance of the IRCbutt class.
     */
    private IRCbutt butt;


    /**
     * Constructor sets the private instance of the IRCbutt class.
     * @param butt The IRCbutt instance.
     */
    public IrcMessageHandler(final IRCbutt butt) {
        this.butt = butt;
    }

    /**
     * This function handles all incoming messages from IRC channels.  It decides what
     * to do with the message based on it's prefix, or suffix.
     * @param event The MessageEvent object coming from PircBotX listener.
     */
    public void handleMessage(final MessageEvent event) {
        /* Handle a command */
        BotResponse response = new BotResponse(event);
        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
            butt.getCommandHandler().handleCommand(event, event.getMessage().split(" "), response);
            ResponseHandler.handleResponse(response);

        /* Handle karma */
        } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;") || event.getMessage().endsWith("--")
                || event.getMessage().endsWith("--;")) {
            KarmaCommand.handleKarma(butt, response, event.getUser(), event.getMessage());

        } else {
            /* Check for URL or troll them */
            // TODO is this the only instance of using this API?  Do we need this?
            Preconditions.checkArgument(event.getUser() != null, "User was null");
            butt.getChatLoggingManager().logMessage(event.getUser().getNick(), event.getMessage());

            // don't troll URL's
            if (UrlTitleHandler.handleUrl(event.getChannel(), event.getMessage())) {
                return;
            }

            // buttify sentence
            if (MathUtils.isRandomResponseTime()) {
                final String buttFormat = butt.getButtReplaceHandler().buttFormat(event.getMessage()).trim();
                if (!buttFormat.equals(event.getMessage()) && buttFormat.contains(" ")) {
                    butt.getBotChatHandler().buttChat(event.getChannel(), buttFormat);
                }
            }
        }
    }

    /**
     * This is the handler for private messages with the bot.
     * @param event The PrivateMessageEvent from the PircBotX listener.
     */
    public void handlePrivateMessage(final PrivateMessageEvent event) {
        BotResponse response = new BotResponse(event);
        Preconditions.checkArgument(event.getUser() != null, "User was null");
        if (butt.getYamlConfigurationFile().getBotNoVerify() || event.getUser().isVerified()) {
            if (event.getMessage().startsWith("!") || event.getMessage().startsWith("~")) {
                butt.getCommandHandler().handleCommand(event, event.getMessage().split(" "), response);
                response.setPrivateMessageNoOverride();
                ResponseHandler.handleResponse(response);
            } else if (event.getMessage().endsWith("++") || event.getMessage().endsWith("++;") || event.getMessage().endsWith("--")
                    || event.getMessage().endsWith("--;")) {
                KarmaCommand.handleKarma(butt, response, event.getUser(), event.getMessage());
                response.setPrivateMessageNoOverride();
                ResponseHandler.handleResponse(response);
            }
        }
    }

    /**
     * This function provides a way to quickly handle an invalid command.
     * // TODO shouldn't this functionality belong in the BotResponse object?
     * @param user The user that gave the command.
     */
    public void handleInvalidCommand(final User user) {
        butt.getBotChatHandler().buttPM(user, "butt dont kno nothin bout that");
    }

    /**
     * The function provides a way to handle an invalid command with a custom string.
     * @param user The user that gave the command.
     * @param message The custom error message to deliver to the user.
     */
    public void handleInvalidCommand(final User user, final String message) {
        butt.getBotChatHandler().buttPM(user, message);
    }
}

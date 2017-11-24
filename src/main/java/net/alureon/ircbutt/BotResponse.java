package net.alureon.ircbutt;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * This class encompasses a bot's response.  The message, the intention,
 * the user the message is directed to, and the event object.
 */
public class BotResponse {

    /**
     * The message the bot wishes to send.
     */
    private String message;
    /**
     * In some cases, messages may be longer than one line.
     * Additional text may be stored here.
     */
    private String additionalMessage;
    /**
     * The intended recipient of the message.
     */
    private User recipient;
    /**
     * The bot's intention (chat, private message, etc).
     */
    private BotIntention intention;
    /**
     * The original MessageEvent object (may be a private interaction).
     */
    private GenericMessageEvent event;

    /**
     * Creates a new BotResponse object. If the user is interacting with
     * the bot via a private message, it's important not to chat back into
     * a channel.  The constructor ensures this doesn't happen.
     * @param theEvent - The MessageEvent from PircBotX
     */
    public BotResponse(final GenericMessageEvent theEvent) {
        this.event = theEvent;
        this.recipient = theEvent.getUser();
        if (theEvent instanceof PrivateMessageEvent) {
            this.intention = BotIntention.PRIVATE_MESSAGE_NO_OVERRIDE;
        }
    }

    /**
     * This is currently only used for JUnit testing.  Can we hide this?
     * //TODO this is fucked up
     */
    public BotResponse() {
        this.event = null;
        this.recipient = null;
    }

    /**
     * Get the message the bot wishes to reply with.
     * @return String - the message the bot wishes to relay.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Return this response's intention.
     * @return BotIntention
     */
    public BotIntention getIntention() {
        return this.intention;
    }

    /**
     * Return the intended recipient.
     * @return The user the message was intended for.
     */
    public User getRecipient() {
        return this.recipient;
    }

    /**
     * Return the bot's additional message, may be null.
     * @return The additional message the bot has.
     */
    public String getAdditionalMessage() {
        return this.additionalMessage;
    }

    /**
     * Return the channel that the event took place in.  This may
     * be a private message buffer, in which case, this method will
     * return null.
     * @return The Channel object the message took place in.
     */
    public Channel getChannel() {
        if (this.event instanceof MessageEvent) {
            return ((MessageEvent) event).getChannel();
        }
        return null;
    }

    /**
     * Sets the bot's intention to private message with supplied message and
     * recipient.
     * @param recipient The intended recipient of the private message.
     * @param message The message to send.
     */
    public void privateMessage(final User recipient, final String message) {
        this.intention = BotIntention.PRIVATE_MESSAGE;
        this.recipient = recipient;
        this.message = message;
    }

    /**
     * Get the MessageEvent tied to this response object
     * @return The original MessageEvent
     */
    public GenericMessageEvent getEvent() {
        return this.event;
    }

    public void highlightChat(User recipient, String message) {
        if (this.event instanceof PrivateMessageEvent) {  // sometimes we don't want highlight chat if PM
            privateMessage(recipient, message);
            return;
        }
        this.intention = BotIntention.HIGHLIGHT;
        this.recipient = recipient;
        this.message = message;
    }

    public void me(String message) {
        this.intention = BotIntention.ME;
        this.message = message;
    }

    public void me(String message, String additionalMessage) {
        this.intention = BotIntention.ME;
        this.message = message;
        this.additionalMessage = additionalMessage;
    }

    public void chat(String message) {
        this.intention = BotIntention.CHAT;
        this.message = message;
    }

    public void chat(String message, String additionalMessage) {
        this.intention = BotIntention.CHAT;
        this.message = message;
        this.additionalMessage = additionalMessage;
    }

    public void noResponse() {
        this.intention = BotIntention.NO_REPLY;
    }

    public void setPrivateMessageNoOverride() {
        this.intention = BotIntention.PRIVATE_MESSAGE_NO_OVERRIDE;
    }

    public boolean hasAdditionalMessage() {
        return !(this.additionalMessage == null);
    }

    @Override
    public String toString() {
        if (this.hasAdditionalMessage()) {
            return this.message + " " + this.additionalMessage;
        }
        return this.message;
    }
}

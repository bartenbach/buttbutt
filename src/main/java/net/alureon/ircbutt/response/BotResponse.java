package net.alureon.ircbutt.response;

import org.pircbotx.User;

/**
 * This class encompasses a bot's response.  The message, the intention,
 * the user the message is directed to, and the event object.
 */
public final class BotResponse {

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
     * Constructor for a BotResponse object.
     * @param intention The intention of the bot.
     * @param recipient The intended recipient (may be null).
     * @param message The message the bot intends to relay.
     */
    public BotResponse(final BotIntention intention, final User recipient, final String message) {
        this.intention = intention;
        this.recipient = recipient;
        this.message = message;
    }

    /**
     * Constructor for a BotResponse object.
     * @param intention The intention of the bot.
     * @param recipient The intended recipient (may be null).
     * @param message The message the bot intends to relay.
     * @param additionalMessage The additional message the bot intends to relay.
     */
    public BotResponse(final BotIntention intention, final User recipient, final String message,
                       final String additionalMessage) {
        this.message = message;
        this.additionalMessage = additionalMessage;
        this.recipient = recipient;
        this.intention = intention;
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

}

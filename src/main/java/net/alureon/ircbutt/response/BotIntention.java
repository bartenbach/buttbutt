package net.alureon.ircbutt.response;

/**
 * The BotIntention Enum contains constants for the intended action
 * the bot will take.  The desired response of the bot may be to
 * highlight chat a user, not reply at all, chat in the channel,
 * private message a user, respond with /me in the channel, or a
 * special flag that ensures only a private message.  Sometimes,
 * it is important to stress that the message must only be private.
 */
public enum BotIntention {
    /**
     * The bot intends to highlight chat the user.
     */
    HIGHLIGHT,
    /**
     * The bot doesn't intend to reply at all.
     */
    NO_REPLY,
    /**
     * The bot intends to chat the message to the channel.
     */
    CHAT,
    /**
     * The bot intends to private message the user.
     */
    PRIVATE_MESSAGE,
    /**
     * The bot intends to execute /me in the channel.
     */
    ME,
    /**
     * The bot insists that ONLY a private message response is appropriate.
     * // TODO look into why this was necessary and document it.
     */
    PRIVATE_MESSAGE_NO_OVERRIDE
}

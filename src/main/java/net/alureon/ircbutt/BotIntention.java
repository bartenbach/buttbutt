package net.alureon.ircbutt;

/**
 * The BotIntention Enum contains constants for the intended action
 * the bot will take.  The desired response of the bot may be to
 * highlight chat a user, not reply at all, chat in the channel,
 * private message a user, respond with /me in the channel, or a
 * special flag that ensures only a private message.  Sometimes,
 * it is important to stress that the message must only be private.
 */
public enum BotIntention {
    HIGHLIGHT,
    NO_REPLY,
    CHAT,
    PRIVATE_MESSAGE,
    ME,
    PRIVATE_MESSAGE_NO_OVERRIDE
}

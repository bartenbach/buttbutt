package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.response.BotResponse;


/**
 * Handles outgoing message from the bot back into IRC - be it a channel
 * message, a private message, a /me command, or nothing at all.
 */
public final class ResponseHandler {

    /**
     * Private constructor to prevent instantiation.
     */
    private ResponseHandler() {

    }

    /**
     * Handles the BotResponse object, and routes it to the correct method
     * based upon the bot's intention.
     * @param response The BotResponse object to handle.
     */
    public static void handleResponse(final BotResponse response) {
        switch (response.getIntention()) {
            case PRIVATE_MESSAGE_NO_OVERRIDE:
                handlePrivateMessage(response);
                break;
            case PRIVATE_MESSAGE:
                handlePrivateMessage(response);
                break;
            case HIGHLIGHT:
                handleHighlight(response);
                break;
            case ME:
                handleMe(response);
                break;
            case NO_REPLY:
                break;
            case CHAT:
                handleChat(response);
                break;
            default:
                break;
        }
    }

    private static void handleChat(final BotResponse response) {
        response.getChannel().send().message(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getChannel().send().message(response.getAdditionalMessage());
        }
    }

    private static void handleHighlight(final BotResponse response) {
        response.getEvent().respond(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getEvent().respond(response.getAdditionalMessage());
        }
    }

    private static void handlePrivateMessage(final BotResponse response) {
        if (response.getMessage() != null) {
            response.getRecipient().send().message(response.getMessage());
            if (response.hasAdditionalMessage()) {
                response.getRecipient().send().message(response.getMessage());
            }
        } else {
            //TODO why does this happen?
            System.out.println("Recieved null message");
            response.getRecipient().send().message("butt have nothin to say on the matter");
        }
    }

    /**
     * Handles the bot's /me command.  Chats the supplied message to the channel
     * using /me, and any additionally supplied messages.
     * @param response The response.
     */
    private static void handleMe(final BotResponse response) {
        response.getChannel().send().action(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getChannel().send().action(response.getAdditionalMessage());
        }
    }

}

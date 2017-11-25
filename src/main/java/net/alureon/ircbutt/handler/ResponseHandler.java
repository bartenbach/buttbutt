package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.response.BotResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;


/**
 * Handles outgoing message from the bot back into IRC - be it a channel
 * message, a private message, a /me command, or nothing at all.
 */
final class ResponseHandler {

    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * Private constructor to prevent instantiation.
     */
    private ResponseHandler() {

    }

    /**
     * Handles the BotResponse object, and routes it to the correct method
     * based upon the bot's intention.
     * @param response The BotResponse object to handle.
     * @param event The GenericMessageEvent associated with the response.
     */
    static void handleResponse(final BotResponse response, final GenericMessageEvent event) {
        switch (response.getIntention()) {
            case PRIVATE_MESSAGE_NO_OVERRIDE: // TODO use cases for this?
                handlePrivateMessage(response);
                break;
            case PRIVATE_MESSAGE:
                handlePrivateMessage(response);
                break;
            case HIGHLIGHT:
                handleHighlight(response, event);
                break;
            case ME:
                handleMe(response, event);
                break;
            case NO_REPLY:
                break;
            case CHAT:
                handleChat(response, event);
                break;
            default:
                break;
        }
    }

    /**
     * Handles a BotResponse object's CHAT intention.
     * @param response The BotResponse object to handle.
     * @param event The GenericMessageEvent associated with the response.
     */
    private static void handleChat(final BotResponse response, final GenericMessageEvent event) {
        if (event instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) event;
            messageEvent.getChannel().send().message(response.getMessage());
            if (response.getAdditionalMessage() != null) {
                messageEvent.getChannel().send().message(response.getAdditionalMessage());
            }
        } else {
            log.error("BotIntention was Chat but message was not instance of MessageEvent:");
            log.error(response.getMessage());
        }
    }

    /**
     * Handles a BotResponse object's HIGHLIGHT intention.
     * @param response The BotResponse object to handle.
     * @param event The GenericMessageEvent associated with the response.
     */
    private static void handleHighlight(final BotResponse response, final GenericMessageEvent event) {
        event.respond(response.getMessage());
        if (response.getAdditionalMessage() != null) {
            event.respond(response.getAdditionalMessage());
        }
    }

    /**
     * Handles a BotResponse object's PRIVATE_MESSAGE intention.
     * @param response The BotResponse object to handle.
     */
    private static void handlePrivateMessage(final BotResponse response) {
        if (response.getMessage() != null) {
            response.getRecipient().send().message(response.getMessage());
            if (response.getAdditionalMessage() != null) {
                response.getRecipient().send().message(response.getMessage());
            }
        } else {
            //TODO why does this happen?
            System.out.println("Recieved null message");
            response.getRecipient().send().message("butt have nothin to say on the matter");
        }
    }

    /**
     * Handles a BotResponse object's ME intention.
     * @param response The BotResponse object to handle.
     * @param event The GenericMessageEvent associated with the response.
     */
    private static void handleMe(final BotResponse response, final GenericMessageEvent event) {
        if (event instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) event;
            messageEvent.getChannel().send().action(response.getMessage());
            if (response.getAdditionalMessage() != null) {
                messageEvent.getChannel().send().action(response.getAdditionalMessage());
            }
        } else {
            event.respondPrivateMessage(response.getMessage());
            if (response.getAdditionalMessage() != null) {
                event.respondPrivateMessage(response.getAdditionalMessage());
            }
        }
    }

}

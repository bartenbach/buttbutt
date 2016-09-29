package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;

class ResponseHandler {

    static void handleResponse(BotResponse response) {
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

    private static void handleChat(BotResponse response) {
        response.getChannel().send().message(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getChannel().send().message(response.getAdditionalMessage());
        }
    }

    private static void handleHighlight(BotResponse response) {
        response.getEvent().respond(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getEvent().respond(response.getAdditionalMessage());
        }
    }

    private static void handlePrivateMessage(BotResponse response) {
        if (response.getMessage() != null) {
            response.getRecipient().send().message(response.getMessage());
            if (response.hasAdditionalMessage()) {
                response.getRecipient().send().message(response.getMessage());
            }
        } else {
            System.out.println("Recieved null message");
            response.getRecipient().send().message("butt have nothin to say on the matter");
        }
    }

    private static void handleMe(BotResponse response) {
        response.getChannel().send().action(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getChannel().send().action(response.getAdditionalMessage());
        }
    }

}

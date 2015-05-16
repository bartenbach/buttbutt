package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;

/**
 * Created by alureon on 3/1/15.
 */

public class ResponseHandler {

    private IRCbutt butt;

    public ResponseHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleResponse(BotResponse response) {
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

    private void handleChat(BotResponse response) {
        response.getChannel().send().message(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getChannel().send().message(response.getAdditionalMessage());
        }
    }

    private void handleHighlight(BotResponse response) {
        response.getMessageEvent().respond(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getMessageEvent().respond(response.getAdditionalMessage());
        }
    }

    private void handlePrivateMessage(BotResponse response) {
        if (response.getMessage() != null) {
            response.getRecipient().send().message(response.getMessage());
            if (response.hasAdditionalMessage()) {
                response.getRecipient().send().message(response.getMessage());
            }
        } else {
            System.out.println("Recieved null message");
        }
    }

    private void handleMe(BotResponse response) {
        response.getChannel().send().action(response.getMessage());
        if (response.hasAdditionalMessage()) {
            response.getChannel().send().action(response.getAdditionalMessage());
        }
    }

}
package net.alureon.ircbutt;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by alureon on 2/28/15.
 */

public class BotResponse {

    private String message;
    private String additionalMessage;
    private User recipient;
    private BotIntention intention;
    private MessageEvent messageEvent;
    private boolean success;


    public BotResponse(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
        this.recipient = messageEvent.getUser();
    }

    private void setRecipient(User user) {
        this.recipient = user;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setIntention(BotIntention intention) {
        this.intention = intention;
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }

    private void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public BotIntention getIntention() {
        return this.intention;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public String getAdditionalMessage() {
        return this.additionalMessage;
    }

    public MessageEvent getMessageEvent() {
        return this.messageEvent;
    }

    public Channel getChannel() {
        return this.messageEvent.getChannel();
    }

    public void privateMessage(User recipient, String message) {
        this.intention = BotIntention.PRIVATE_MESSAGE;
        this.recipient = recipient;
        this.message = message;
    }

    public void highlightChat(User recipient, String message) {
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

    public boolean hasAdditionalMessage() {
        return !(this.additionalMessage == null);
    }
}

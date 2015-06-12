package net.alureon.ircbutt;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Created by alureon on 2/28/15.
 */

public class BotResponse {


    // TODO testing making this more generic.  Maybe just use GenericMessageEvent to simplify things
    // Keep throwing exceptions here.

    private String message;
    private String additionalMessage;
    private User recipient;
    private BotIntention intention;
    private GenericMessageEvent event;

    public BotResponse(GenericMessageEvent event) {
        this.event = event;
        this.recipient = event.getUser();
        if (event instanceof PrivateMessageEvent) {
            this.intention = BotIntention.PRIVATE_MESSAGE_NO_OVERRIDE;
        }
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

    public Channel getChannel() {
        if (this.event instanceof MessageEvent) {
            return ((MessageEvent) event).getChannel();
        }
        return null;
    }

    public void privateMessage(User recipient, String message) {
        this.intention = BotIntention.PRIVATE_MESSAGE;
        this.recipient = recipient;
        this.message = message;
    }

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
            StringBuilder sb = new StringBuilder(this.message);
            sb.append(" ").append(this.additionalMessage);
            return sb.toString();
        }
        return this.message;
    }
}

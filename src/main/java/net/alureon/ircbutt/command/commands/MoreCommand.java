package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class handles the !more command, which can return extra results from commands that load results into it.
 * For instance, if a user Google searches something, there may be more than one result.  How does a user access
 * the other results?  This class solves that problem.
 */
public final class MoreCommand implements Command {

    /**
     * This holds all the extra items that will populate More.
     */
    private final ArrayList<String> more = new ArrayList<>();
    /**
     * This is the default message for no more items.
      */
    private final String defaultNoMore = "butt don't have any more";
    /**
     * This can hold a custom message for having no more items, such as a link to search online for more.
     */
    private String noMoreMessage = defaultNoMore;

    /**
     * Adds a String object to the More list.
     * @param moreItem The string to add to the More list.
     */
    public void addMore(final String moreItem) {
        this.more.add(moreItem);
    }

    /**
     * Set the custom message for when no more More items exist.
     * @param customMoreMessage The custom more message to use when the list is empty.
     */
    public void setNoMoreMessage(final String customMoreMessage) {
        this.noMoreMessage = customMoreMessage;
    }

    /**
     * Clear the more list.  This is important to do before populating More, otherwise you will
     * have elements from a different query in your list.
     */
    public void clearMore() {
        this.more.clear();
        this.noMoreMessage = defaultNoMore;
    }

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        if (!this.more.isEmpty()) {
            String moreItem = this.more.get(0);
            this.more.remove(0);
            return new BotResponse(BotIntention.CHAT, null, moreItem);
        } else {
            return new BotResponse(BotIntention.CHAT, null, this.noMoreMessage);
        }
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("more"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }

}

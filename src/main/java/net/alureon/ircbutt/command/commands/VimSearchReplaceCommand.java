package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Allows use of Vim's search and replace command in IRC.
 */
public final class VimSearchReplaceCommand {

    /**
     * This is akin to the executeCommand function in the Command interface, although that
     * won't work with this because there is no actual command - it's all one string.
     * @param butt The IRCbutt instance.
     * @param event The MessageEvent from PircBotX.
     * @param cmd The command array from the user.
     * @return The bot's response, which in this case is a string that has been searched and replaced.
     */
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        if (butt.getChatStorage().hasQuoteFrom(event.getUser().getNick())) {
            String replaced = searchAndReplace(cmd[0],
                    butt.getChatStorage().getLastQuoteFrom(event.getUser().getNick()));
            return new BotResponse(BotIntention.CHAT, null, replaced);
        } else {
            return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "butt dont see any message from you");
        }
    }

    /**
     * Performs search and replace like Vim.
     * @param command The command you'd give Vim.
     * @param lastMessage The message to perform search and replace on.
     * @return The String with replacements made.
     */
    public String searchAndReplace(final String command, final String lastMessage) {
        String[] split = command.split("/");
        String corrected;
        if (split.length == 4 && split[3].equals("g")) {
            corrected = lastMessage.replaceAll(split[1], split[2]);
        } else {
            corrected = lastMessage.replaceFirst(split[1], split[2]);
        }
        return corrected;
    }

}

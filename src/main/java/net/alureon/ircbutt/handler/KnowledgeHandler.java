package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnowledgeHandler {


    private IRCbutt butt;
    public final static Logger log = LoggerFactory.getLogger(KnowledgeHandler.class);


    public KnowledgeHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleKnowledge(String[] cmd, Channel channel, User user, String nick) {
        if (cmd[0].equals("learn")) {
            if (channel.isOp(user)) {
                boolean added = addKnowledge(nick, cmd);
                if (added) {
                    butt.getButtChatHandler().buttChat(channel, nick + ": ok got it!");
                } else {
                    //TODO potentially differentiate between the two here?
                    butt.getButtChatHandler().buttPM(user, "either ur format sux or i already kno wat that is");
                }
            }
        } else if (cmd[0].equals("forget")) {
            if (channel.isOp(user)) {
                boolean success = removeKnowledge(cmd);
                if (success) {
                    butt.getButtChatHandler().buttChat(channel, "ok butt wont member that no more");
                } else {
                    butt.getMessageHandler().handleInvalidCommand(user);
                }
            } else {
                log.trace(nick + " is not a channel op");
            }
        } else if (cmd[0].startsWith("~")) {
            cmd[0] = cmd[0].replaceFirst("~", "");
            String info = getKnowledge(StringUtils.arrayToString(cmd));
            if (info != null) {
                butt.getButtChatHandler().buttChat(channel, info);
            } else {
                butt.getMessageHandler().handleInvalidCommand(user);
            }
        }
    }

    public boolean addKnowledge(String commandSender, String[] data) {
        if (data[1].endsWith(":") && data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(":");
            String item = split[0].substring(0, split[0].length()).trim();
            if (getKnowledge(item) == null) {
                String information = StringUtils.getArgsOverOne(data);
                log.trace("Item: " + item);
                log.trace("Data: " + information);
                butt.getKnowledgeTable().insertKnowledge(item, information, commandSender);
                return true;
            }
        }
        return false;
    }

    public String getKnowledge(String item) {
        if (!item.isEmpty()) {
            log.trace("Item: " + item);
            return butt.getKnowledgeTable().queryKnowledge(item);
        }
        return null;
    }

    public boolean removeKnowledge(String[] item) {
        return item.length > 0 && butt.getKnowledgeTable().deleteKnowledge(StringUtils.getArgs(item));
    }

}

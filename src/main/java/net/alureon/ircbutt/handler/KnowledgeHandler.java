package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
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

    public BotResponse handleKnowledge(BotResponse response, String[] cmd, Channel channel, User user, String nick) {
        if (cmd[0].equals("learn")) {
            if (channel.isOp(user)) {
                boolean added = addKnowledge(nick, cmd);
                if (added) {
                    response.highlightChat(user, "ok got it!");
                } else {
                    //TODO potentially differentiate between the two here?  If it already exists
                    response.privateMessage(user, "either your format is wrong or that fact already exists");
                }
            }
        } else if (cmd[0].equals("forget")) {
            if (channel.isOp(user)) {
                boolean success = removeKnowledge(cmd);
                if (success) {
                    response.chat("ok butt wont member that no more");
                } else {
                    response.noResponse();
                    butt.getMessageHandler().handleInvalidCommand(user);
                }
            } else {
                log.trace(nick + " is not a channel op");
            }
        } else if (cmd[0].startsWith("~")) {
            cmd[0] = cmd[0].replaceFirst("~", "");
            String info = getKnowledge(StringUtils.arrayToString(cmd));
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equals("fact")) {
            cmd[0] = cmd[0].replaceFirst("~", "");
            String info = butt.getKnowledgeTable().getRandomData();
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getMessageHandler().handleInvalidCommand(user);
            }
        }
        return response;
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

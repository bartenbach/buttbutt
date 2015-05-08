package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactHandler {


    private IRCbutt butt;
    public final static Logger log = LoggerFactory.getLogger(FactHandler.class);


    public FactHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public BotResponse handleKnowledge(BotResponse response, String[] cmd, User user, String nick) {
        if (cmd[0].equals("learn")) {
            if (user.isVerified()) {
                boolean added = addKnowledge(nick, cmd);
                if (added) {
                    response.highlightChat(user, "ok got it!");
                } else {
                    //TODO potentially differentiate between the two here?  If it already exists
                    response.privateMessage(user, "either your format is wrong or that fact already exists");
                }
            }
        } else if (cmd[0].equals("forget")) {
            if (butt.getIrcUtils().isOpInBotChannel(user)) {
                boolean success = removeKnowledge(cmd);
                if (success) {
                    response.chat("ok butt wont member that no more");
                } else {
                    response.noResponse();
                    butt.getMessageHandler().handleInvalidCommand(user);
                }
            } else {
                response.noResponse();
                log.trace(nick + " is not a channel op");
            }
        } else if (cmd[0].startsWith("~")) {
            cmd[0] = cmd[0].replaceFirst("~", "");
            String info = getKnowledge(StringUtils.arrayToString(cmd));
            if (info != null) {
                String mInfo = info.replaceAll("%user%", user.getNick());
                response.chat(mInfo);
            } else {
                response.noResponse();
                butt.getMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equals("fact")) {
                String info = butt.getFactTable().getRandomData();
                if (info != null) {
                    response.chat(info);
                } else {
                    response.noResponse();
                    butt.getMessageHandler().handleInvalidCommand(user);
                }
        } else if (cmd[0].equals("factinfo") || cmd[0].equals("finfo")) {
            String info = butt.getFactTable().getKnowledgeInfo(StringUtils.getArgs(cmd));
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equalsIgnoreCase("factfind") || cmd[0].equalsIgnoreCase("factsearch")
                || cmd[0].equalsIgnoreCase("fsearch") || cmd[0].equalsIgnoreCase("ffind")) {
            String info = butt.getFactTable().findFact(StringUtils.getArgs(cmd));
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
                butt.getFactTable().insertKnowledge(item, information, commandSender);
                return true;
            }
        }
        return false;
    }

    public String getKnowledge(String item) {
        if (!item.isEmpty()) {
            log.trace("Item: " + item);
            return butt.getFactTable().queryKnowledge(item);
        }
        return null;
    }

    public boolean removeKnowledge(String[] item) {
        return item.length > 0 && butt.getFactTable().deleteKnowledge(StringUtils.getArgs(item));
    }

}

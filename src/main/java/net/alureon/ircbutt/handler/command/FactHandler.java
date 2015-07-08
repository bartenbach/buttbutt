package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.IRCUtils;
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

    public void handleKnowledge(BotResponse response, String[] cmd, User user, String nick) {
        if (cmd[0].equals("learn")) {
            // cmd.split(" ", 2);
            if (butt.getYamlConfigurationFile().getBotNoVerify() || user.isVerified()) {
                boolean added = addKnowledge(response, user, nick, cmd);
                if (added) {
                    response.highlightChat(user, "ok got it!");
                }
            }
        } else if (cmd[0].equals("append")) {
            if (butt.getYamlConfigurationFile().getBotNoVerify() || user.isVerified()) {
                boolean added = appendToKnowledge(response, user, nick, cmd);
                if (added) {
                    response.highlightChat(user, "ok got it!");
                }
            }
        } else if (cmd[0].equals("forget")) {
            if (IRCUtils.isOpInBotChannel(butt, user)) {
                if (cmd.length == 2) {
                    String old = getFact(cmd[1]);
                    boolean success = removeKnowledge(cmd);
                    if (success) {
                        response.chat("Forgot: " + cmd[1] + " = " + old);
                    } else {
                        response.chat("Could not forget: " + cmd[1] + " = " + old);
                    }
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
            String info = getFact(StringUtils.arrayToString(cmd));
            if (info != null) {
                if (info.contains("%args%")) {
                    info = info.replaceAll("%args%", StringUtils.getArgs(cmd));
                }
                if (info.startsWith("%me%")) {
                    info = info.replaceFirst("%me%", "");
                    response.me(info.replaceAll("%user%", user.getNick()));
                    return;
                }
                response.chat(info.replaceAll("%user%", user.getNick()));
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
        } else if (cmd[0].equals("factinfo") || cmd[0].equals("finfo") || cmd[0].equals("fi")) {
            String info = butt.getFactTable().getFactInfo(StringUtils.getArgs(cmd));
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equalsIgnoreCase("factfind") || cmd[0].equalsIgnoreCase("factsearch")
                || cmd[0].equalsIgnoreCase("fsearch") || cmd[0].equalsIgnoreCase("ffind")
                || cmd[0].equals("ff") || cmd[0].equals("fs")) {
            String info = butt.getFactTable().findFact(StringUtils.getArgs(cmd));
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getMessageHandler().handleInvalidCommand(user, "butt find noting");
            }
        } else if (cmd[0].equalsIgnoreCase("factbyid") || cmd[0].equalsIgnoreCase("factid") || cmd[0].equalsIgnoreCase("fid")) {
            try {
                int id = Integer.parseInt(StringUtils.getArgs(cmd));
                String info = butt.getFactTable().findFactById(id);
                if (info != null) {
                    response.chat(info);
                } else {
                    response.noResponse();
                    butt.getMessageHandler().handleInvalidCommand(user, "butt find noting");
                }
            } catch (Exception e) {
                butt.getMessageHandler().handleInvalidCommand(user, "not a number");
            }
        }
    }

    public boolean addKnowledge(BotResponse response, User user, String commandSender, String[] data) {
        if (data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(" ", 2);
            if (split[0].endsWith(":")) { split[0] = split[0].replace(":",""); } //backwards compatibility
            String item = split[0].substring(0, split[0].length()).trim();
            if (getFact(item) == null) {
                String information = StringUtils.getArgsOverOne(data);
                if (information.length() > 300) {
                    response.highlightChat(user, "error: tl;dr");
                    return false;
                }
                log.trace("Item: " + item);
                log.trace("Data: " + information);
                butt.getFactTable().insertKnowledge(item, information, commandSender);
                response.highlightChat(user, "ok got it!");
                return true;
            } else {
                response.privateMessage(user, "error: fact already exists");
            }
        }
        return false;
    }

    public boolean appendToKnowledge(BotResponse response, User user, String commandSender, String[] data) {
        if (data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(" ", 2);
            String item = split[0].substring(0, split[0].length()).trim();
            if (getFact(item) != null) {
                String information = StringUtils.getArgsOverOne(data);
                if (information.length() > 300) {
                    response.highlightChat(user, "error: tl;dr");
                    return false;
                }
                log.trace("Item: " + item);
                log.trace("Data: " + information);
                butt.getFactTable().appendKnowledge(item, information, commandSender);
                response.highlightChat(user, "ok got it!");
                return true;
            } else {
                response.privateMessage(user, "error: fact does not exist");
            }
        }
        return false;
    }
    
    public String getFact(String item) {
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

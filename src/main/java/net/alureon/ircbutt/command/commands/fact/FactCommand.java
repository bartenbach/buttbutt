package net.alureon.ircbutt.command.commands.fact;

import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.IRCUtils;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.User;

public class FactCommand {


    private IRCbutt butt;
    public static final Logger log = LogManager.getLogger(FactCommand.class);

    public FactCommand(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleKnowledge(BotResponse response, String[] cmd, User user, String nick) {
        if (cmd[0].equals("learn")) {
            if (butt.getYamlConfigurationFile().getBotNoVerify() || user.isVerified()) {
                boolean added = addKnowledge(response, user, nick, cmd);
                if (added) {
                    response.highlightChat(user, "ok got it!");
                }
            }
        } else if (cmd[0].equals("append")) {
            if (butt.getYamlConfigurationFile().getBotNoVerify() || user.isVerified()) {
                boolean added = appendToKnowledge(response, user, cmd);
                if (added) {
                    response.highlightChat(user, "ok got it!");
                }
            }
        } else if (cmd[0].equals("forget")) {
            if (IRCUtils.isOpInBotChannel(butt, user)) {
                if (cmd.length == 2) {
                    String old = getFact(cmd[1]);
                    if (removeKnowledge(cmd)) {
                        response.chat("ok butt wont member that no more");
                        // log to console in event of accidental data loss
                        log.info("Removed fact [" + cmd[1] + "]: " + old);
                    } else {
                        response.privateMessage(user, "butt don't know nothin bout " + cmd[1]);
                    }
                } else {
                    response.noResponse();
                    butt.getIrcMessageHandler().handleInvalidCommand(user);
                }
            } else {
                response.noResponse();
                log.trace(nick + " is not a channel op");
            }
        } else if (cmd[0].startsWith("~")) {
            cmd[0] = cmd[0].replaceFirst("~", "");
            String info = getFact(StringUtils.arrayToString(cmd));
            if (info != null) {
                if (info.startsWith("$ME")) {
                    info = info.replaceFirst("\\$ME", "");
                    response.me(info.replaceAll("\\$USER", user.getNick()));
                    return;
                }
                response.chat(info.replaceAll("\\$USER", user.getNick()));
            } else {
                response.noResponse();
                butt.getIrcMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equals("fact")) {
            String info = butt.getFactTable().getRandomData();
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getIrcMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equals("factinfo") || cmd[0].equals("finfo") || cmd[0].equals("fi")) {
            String info = butt.getFactTable().getFactInfo(StringUtils.getArgs(cmd));
            if (info != null) {
                response.chat(info);
            } else {
                response.noResponse();
                butt.getIrcMessageHandler().handleInvalidCommand(user);
            }
        } else if (cmd[0].equalsIgnoreCase("factfind") || cmd[0].equalsIgnoreCase("factsearch")
                || cmd[0].equalsIgnoreCase("fsearch") || cmd[0].equalsIgnoreCase("ffind")
                || cmd[0].equals("ff") || cmd[0].equals("fs")) {
            butt.getMoreCommand().clearMore();
            String info = butt.getFactTable().findFact(StringUtils.getArgs(cmd));
            if (info != null) {
                response.chat(info);
            } else {
                response.highlightChat(user, "butt find nothing");
            }
            //todo: factfind ^ should just accept either string or integer input instead of having separate commands
        } else if (cmd[0].equalsIgnoreCase("factbyid") || cmd[0].equalsIgnoreCase("factid") || cmd[0].equalsIgnoreCase("fid")) {
            try {
                int id = Integer.parseInt(StringUtils.getArgs(cmd));
                String info = butt.getFactTable().findFactById(id);
                if (info != null) {
                    response.chat(info);
                } else {
                    response.noResponse();
                    butt.getIrcMessageHandler().handleInvalidCommand(user, "butt find noting");
                }
            } catch (Exception e) {
                butt.getIrcMessageHandler().handleInvalidCommand(user, "not a number");
            }
        }
    }

    private boolean addKnowledge(BotResponse response, User user, String commandSender, String[] data) {
        if (data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(" ", 2);
            if (split[0].endsWith(":")) {
                split[0] = split[0].replace(":", "");
            } //backwards compatibility
            String item = split[0].substring(0, split[0].length()).trim();
            if (getFact(item) == null) {
                String information = StringUtils.getArgsOverOne(data);
                if (information.length() > 300) {
                    response.highlightChat(user, "tl;dr");
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

    private boolean appendToKnowledge(BotResponse response, User user, String[] data) {
        if (data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(" ", 2);
            if (split[0].endsWith(":")) {
                split[0] = split[0].replace(":", "");
            } //backwards compatibility
            String item = split[0].substring(0, split[0].length()).trim();
            if (getFact(item) != null) {
                String information = StringUtils.getArgsOverOne(data);
                if (information.length() > 300) {
                    response.highlightChat(user, "tl;dr");
                    return false;
                }
                log.trace("Item: " + item);
                log.trace("Data: " + information);
                butt.getFactTable().appendKnowledge(item, information);
                response.highlightChat(user, "ok got it!");
                return true;
            } else {
                response.privateMessage(user, "error: fact does not exist");
            }
        }
        return false;
    }

    private String getFact(String item) {
        if (!item.isEmpty()) {
            log.trace("Item: " + item);
            return butt.getFactTable().queryKnowledge(item);
        }
        return null;
    }

    private boolean removeKnowledge(String[] item) {
        return item.length > 0 && butt.getFactTable().deleteKnowledge(StringUtils.getArgs(item));
    }

}

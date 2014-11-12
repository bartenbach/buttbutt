package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnowledgeHandler {


    private IRCbutt butt;
    public final static Logger log = LoggerFactory.getLogger(KnowledgeHandler.class);


    public KnowledgeHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public boolean addKnowledge(String commandSender, String[] data) {
        if (data[1].endsWith(":") && data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(":");
            String item = split[0].substring(0, split[0].length()).trim();  // @TESTING - trimming initial whitespace
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

package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class KnowledgeHandler {


    private IRCbutt butt;
    public final static Logger log = LoggerFactory.getLogger(KnowledgeHandler.class);


    public KnowledgeHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void addKnowledge(String commandSender, String[] data) {
        if (data[1].endsWith(":") && data.length > 2) {
            String command = StringUtils.getArgs(data);
            String[] split = command.split(":");
            String item = split[0].substring(0, split[0].length()).trim();  // @TESTING - trimming initial whitespace
            String information = StringUtils.getArgsOverOne(data);
            log.debug("Item: " + item);
            log.debug("Data: " + information);
            try {
                butt.getKnowledgeTable().insertKnowledge(item, information, commandSender);
            } catch (SQLException ex) {
                log.error("Failed to add knowledge to database.  Stacktrace: ", ex);
            }
        }
    }

    public String getKnowledge(String[] item) {
        if (item.length > 0) {
            String itemName = StringUtils.arrayToString(item);
            log.debug("Item: " + itemName);
            return butt.getKnowledgeTable().queryKnowledge(itemName);
        }
        return null;
    }

    public boolean removeKnowledge(String[] item) {
        return item.length > 0 && butt.getKnowledgeTable().deleteKnowledge(StringUtils.getArgs(item));
    }

}

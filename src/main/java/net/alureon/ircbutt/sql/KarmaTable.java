package net.alureon.ircbutt.sql;

import net.alureon.ircbutt.IRCbutt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alureon on 5/12/15.
 */

public class KarmaTable {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(KarmaTable.class);


    public KarmaTable(IRCbutt butt) {
        this.butt = butt;
    }

    public boolean itemExists(String item) {
        String query = "SELECT * FROM `" + butt.getYamlConfigurationFile().getSqlTablePrefix() + "_karma` WHERE item=?";
        PreparedStatement ps = butt.getSqlManager().getPreparedStatement(query);
        butt.getSqlManager().prepareStatement(ps);
        ResultSet rs = butt.getSqlManager().getResultSet(ps);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            log.error("KarmaTable: SQL Exception, ", ex);
        }
        return false;
    }

}

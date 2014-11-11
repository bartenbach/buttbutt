package net.alureon.ircbutt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtils {


    public final static Logger log = LoggerFactory.getLogger(SqlUtils.class);


    public static void close(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                log.warn("ResultSet could not be closed. ", ex);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                log.warn("PreparedStatement could not be closed. ", ex);
            }
        }
    }

}

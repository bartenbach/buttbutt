package net.alureon.ircbutt.command.commands;

import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.pircbotx.Colors;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Command returns how long the bot has been running (actually, the JVM).
 */
public final class UptimeCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();
        String timeString = getTimeString(uptime);
        return new BotResponse(BotIntention.CHAT, null, Colors.TEAL + timeString);
    }

    /**
     * Returns the uptime string of the current JVM instance in a human readable format.
     * @param uptime The current (long) uptime of the JVM.
     * @return The human readable string of the current uptime.
     */
    private String getTimeString(final long uptime) {
        String timeString = "";
        long time;
        time = TimeUnit.MILLISECONDS.toDays(uptime);
        if (time == 1) {
            timeString = String.valueOf(time) + " day";
        } else if (time > 1) {
            timeString = String.valueOf(time) + " days";
        }
        if (time <= 0) {
            time = TimeUnit.MILLISECONDS.toHours(uptime);
            if (time == 1) {
                timeString = String.valueOf(time) + " hour";
            } else {
                timeString = String.valueOf(time) + " hours";
            }
            if (time <= 0) {
                time = TimeUnit.MILLISECONDS.toMinutes(uptime);
                if (time == 1) {
                    timeString = String.valueOf(time) + " minute";
                } else {
                    timeString = String.valueOf(time) + " minutes";
                }
                if (time <= 0) {
                    time = TimeUnit.MILLISECONDS.toSeconds(uptime);
                    if (time == 1) {
                        timeString = String.valueOf(time) + " second";
                    } else {
                        timeString = String.valueOf(time) + " seconds";
                    }
                    if (time <= 0) {
                        if (uptime == 1) {
                            timeString = String.valueOf(uptime) + " millisecond";
                        } else {
                            timeString = String.valueOf(uptime) + " milliseconds";
                        }
                    }
                }
            }
        }
        return timeString;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("uptime"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

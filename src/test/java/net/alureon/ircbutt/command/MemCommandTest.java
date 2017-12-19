package net.alureon.ircbutt.command;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * Provides a quick test for mem command.
 */
public final class MemCommandTest {

    /**
     * One kibibyte is equal to 1024.
     */
    private static final double KIBIBYTE = 1024.0;

    /**
     * Quick test of the mem command output.
     */
    @Test
    public void testMemCommand() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        double memory = (double) Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double megabytes = memory / KIBIBYTE / KIBIBYTE;
        System.out.println(df.format(megabytes) + " MB");

    }
}

package net.alureon.ircbutt.command.commands.cryptocurrency;

/**
 * A common interface for all cryptocurrency commands to use.
 */
public interface Cryptocurrency {
    /**
     * Return the data for the request.
     * @return The 'Data' Data structure for this cryptocurrency.
     */
    Data getData();

    /**
     * Set the data of the request.
     * @param data The 'Data' data structure for this cryptocurrency.
     */
    void setData(Data data);
}

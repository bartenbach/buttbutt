package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a JSON binding to an Ethereum value request from Coinbase.
 */
public final class ETH implements Cryptocurrency {

    /**
     * The data containing information about this ETH value request.
     */
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * Get the data from this ETH request.
     * @return The 'Data' data structure for this ETH request.
     */
    public Data getData() {
        return data;
    }

    /**
     * Set the data from this ETH request.
     * @param data The 'Data' data structure for this cryptocurrency.
     */
    public void setData(final Data data) {
        this.data = data;
    }


}

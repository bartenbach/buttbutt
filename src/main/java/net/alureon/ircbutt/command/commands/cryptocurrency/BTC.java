package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A class representing a JSON response for Bitcoin.
 */
public final class BTC implements Cryptocurrency {

    /**
     * The data for this request.
     */
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * Return the data for this Bitcoin request.
     * @return The data for this Bitcoin request.
     */
    public Data getData() {
        return data;
    }

    /**
     * Set the data for this bitcoin request.
     * @param data The 'Data' data structure for this cryptocurrency.
     */
    public void setData(final Data data) {
        this.data = data;
    }


}

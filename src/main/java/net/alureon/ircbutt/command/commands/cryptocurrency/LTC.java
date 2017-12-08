package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a JSON binding class for LTC price requests.
 */
public final class LTC implements Cryptocurrency {

    /**
     * The 'Data' data structure for this request.
     */
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * Get the data from this LTC request.
     * @return The 'Data' data structure for this cryptocurrency price request.
     */
    public Data getData() {
        return data;
    }

    /**
     * Set the data for this LTC request.
     * @param data The 'Data' data structure for this cryptocurrency.
     */
    public void setData(final Data data) {
        this.data = data;
    }

}

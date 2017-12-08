package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A class binding the JSON data returned from the Coinbase API (for cryptocurrency).
 */
public final class Data {
    /**
     * JSON element: the type.
     */
    @SerializedName("base")
    @Expose
    private String base;
    /**
     * JSON element: the currency.
     */
    @SerializedName("currency")
    @Expose
    private String currency;
    /**
     * JSON element: the amount.
     */
    @SerializedName("amount")
    @Expose
    private String amount;

    /**
     * Returns base.
     * @return returns the type of cryptocurrency.
     */
    public String getBase() {
        return base;
    }

    /**
     * Sets the base.
     * @param base Set the type of cryptocurrency.
     */
    public void setBase(final String base) {
        this.base = base;
    }

    /**
     * Returns the currency the value is in.
     * @return the currency of this value.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the currency of this object.
     * @param currency The currency to set.
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * Gets the amount of this request.
     * @return The amount.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the amount of this request.
     * @param amount The amount.
     */
    public void setAmount(final String amount) {
        this.amount = amount;
    }

}

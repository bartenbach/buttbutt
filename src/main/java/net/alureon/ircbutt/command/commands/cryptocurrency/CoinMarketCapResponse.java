package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a response from the CoinMarketCap API.
 */
public final class CoinMarketCapResponse {

    /**
     * This is the same as the name.
     */
    @SerializedName("id")
    @Expose
    private String id;
    /**
     * The cryptocurrencies name.
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     * The cryptocurrencies symbol.
     */
    @SerializedName("symbol")
    @Expose
    private String symbol;
    /**
     * The cryptocurrencies rank in CoinMarketCap.
     */
    @SerializedName("rank")
    @Expose
    private String rank;
    /**
     * The current price in USD.
     */
    @SerializedName("price_usd")
    @Expose
    private String priceUsd;
    /**
     * The crypto's price compared to BTC.
     */
    @SerializedName("price_btc")
    @Expose
    private String priceBtc;
    /**
     * The 24hr volume.
     */
    @SerializedName("24h_volume_usd")
    @Expose
    private String t24hVolumeUsd;
    /**
     * The coin's market cap.
     */
    @SerializedName("market_cap_usd")
    @Expose
    private String marketCapUsd;
    /**
     * The available supply.
     */
    @SerializedName("available_supply")
    @Expose
    private String availableSupply;
    /**
     * The total supply.
     */
    @SerializedName("total_supply")
    @Expose
    private String totalSupply;
    /**
     * The max supply.
     */
    @SerializedName("max_supply")
    @Expose
    private String maxSupply;
    /**
     * Change in 1 hour.
     */
    @SerializedName("percent_change_1h")
    @Expose
    private String percentChange1h;
    /**
     * Change in 24 hours.
     */
    @SerializedName("percent_change_24h")
    @Expose
    private String percentChange24h;
    /**
     * Change in 7 days.
     */
    @SerializedName("percent_change_7d")
    @Expose
    private String percentChange7d;
    /**
     * Last updated.
     */
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    /**
     * Returns the ID.
     * @return String - the ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID.
     * @param id The crypto's ID.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     * @return The crypto name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name The name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the symbol.
     * @return The symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Set the symbol.
     * @param symbol The symbol.
     */
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Get the rank.
     * @return the rank.
     */
    public String getRank() {
        return rank;
    }

    /**
     * Set the rank.
     * @param rank the rank
     */
    public void setRank(final String rank) {
        this.rank = rank;
    }

    /**
     * Get the price in USD.
     * @return the price.
     */
    public String getPriceUsd() {
        return priceUsd;
    }

    /**
     * Set the price in USD.
     * @param priceUsd the price.
     */
    public void setPriceUsd(final String priceUsd) {
        this.priceUsd = priceUsd;
    }

    /**
     * Get the price in BTC.
     * @return the price in BTC.
     */
    public String getPriceBtc() {
        return priceBtc;
    }

    /**
     * Set the price in BTC.
     * @param priceBtc The price in BTC.
     */
    public void setPriceBtc(final String priceBtc) {
        this.priceBtc = priceBtc;
    }

    /**
     * Get the 24hr volume.
     * @return the 24hr volume
     */
    public String get24hVolumeUsd() {
        return t24hVolumeUsd;
    }

    /**
     * Set the 24hr volume.
     * @param d24hVolumeUsd The 24 hour volume
     */
    public void set24hVolumeUsd(final String d24hVolumeUsd) {
        this.t24hVolumeUsd = d24hVolumeUsd;
    }

    /**
     * Get the coin market cap in USD.
     * @return the coin market cap in USD.
     */
    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    /**
     * Set the coin market cap in USD.
     * @param marketCapUsd the coin market cap in USD.
     */
    public void setMarketCapUsd(final String marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    /**
     * Get the available supply.
     * @return the available supply.
     */
    public String getAvailableSupply() {
        return availableSupply;
    }

    /**
     * Set the available supply.
     * @param availableSupply the available supply.
     */
    public void setAvailableSupply(final String availableSupply) {
        this.availableSupply = availableSupply;
    }

    /**
     * Get total supply.
     * @return the total supply.
     */
    public String getTotalSupply() {
        return totalSupply;
    }

    /**
     * Set the total supply.
     * @param totalSupply the total supply
     */
    public void setTotalSupply(final String totalSupply) {
        this.totalSupply = totalSupply;
    }

    /**
     * Get the max supply.
     * @return the max supply.
     */
    public String getMaxSupply() {
        return maxSupply;
    }

    /**
     * Set the max supply.
     * @param maxSupply the max supply
     */
    public void setMaxSupply(final String maxSupply) {
        this.maxSupply = maxSupply;
    }

    /**
     * Get percent change in 1 hr.
     * @return the percent change in 1hr.
     */
    public String getPercentChange1h() {
        return percentChange1h;
    }

    /**
     * Set the percent change in 1hr.
     * @param percentChange1h the percent change in 1hr.
     */
    public void setPercentChange1h(final String percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    /**
     * Gets the percent change in 24hr.
     * @return the percent change in 24hr.
     */
    public String getPercentChange24h() {
        return percentChange24h;
    }

    /**
     * Sets the percent change in 24hr.
     * @param percentChange24h the percent change in 24hr.
     */
    public void setPercentChange24h(final String percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    /**
     * Gets the percent change in 7d.
     * @return The percent change in 7d.
     */
    public String getPercentChange7d() {
        return percentChange7d;
    }

    /**
     * Set the percent change in 7d.
     * @param percentChange7d the percent change in 7d.
     */
    public void setPercentChange7d(final String percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    /**
     * Gets last updated.
     * @return when last updated.
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets when last updated.
     * @param lastUpdated when last updated.
     */
    public void setLastUpdated(final String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}

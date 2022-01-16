package net.alureon.ircbutt.command.commands.cryptocurrency;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinMarketCapResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private Data data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Btc {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("symbol")
        @Expose
        private String symbol;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("num_market_pairs")
        @Expose
        private Integer numMarketPairs;
        @SerializedName("date_added")
        @Expose
        private String dateAdded;
        @SerializedName("tags")
        @Expose
        private List<String> tags = null;
        @SerializedName("max_supply")
        @Expose
        private Integer maxSupply;
        @SerializedName("circulating_supply")
        @Expose
        private Integer circulatingSupply;
        @SerializedName("total_supply")
        @Expose
        private Integer totalSupply;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("platform")
        @Expose
        private Object platform;
        @SerializedName("cmc_rank")
        @Expose
        private Integer cmcRank;
        @SerializedName("is_fiat")
        @Expose
        private Integer isFiat;
        @SerializedName("last_updated")
        @Expose
        private String lastUpdated;
        @SerializedName("quote")
        @Expose
        private Quote quote;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Integer getNumMarketPairs() {
            return numMarketPairs;
        }

        public void setNumMarketPairs(Integer numMarketPairs) {
            this.numMarketPairs = numMarketPairs;
        }

        public String getDateAdded() {
            return dateAdded;
        }

        public void setDateAdded(String dateAdded) {
            this.dateAdded = dateAdded;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Integer getMaxSupply() {
            return maxSupply;
        }

        public void setMaxSupply(Integer maxSupply) {
            this.maxSupply = maxSupply;
        }

        public Integer getCirculatingSupply() {
            return circulatingSupply;
        }

        public void setCirculatingSupply(Integer circulatingSupply) {
            this.circulatingSupply = circulatingSupply;
        }

        public Integer getTotalSupply() {
            return totalSupply;
        }

        public void setTotalSupply(Integer totalSupply) {
            this.totalSupply = totalSupply;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public Object getPlatform() {
            return platform;
        }

        public void setPlatform(Object platform) {
            this.platform = platform;
        }

        public Integer getCmcRank() {
            return cmcRank;
        }

        public void setCmcRank(Integer cmcRank) {
            this.cmcRank = cmcRank;
        }

        public Integer getIsFiat() {
            return isFiat;
        }

        public void setIsFiat(Integer isFiat) {
            this.isFiat = isFiat;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public Quote getQuote() {
            return quote;
        }

        public void setQuote(Quote quote) {
            this.quote = quote;
        }

    }

    public class Data {

        @SerializedName("BTC")
        @Expose
        private Btc btc;

        public Btc getBtc() {
            return btc;
        }

        public void setBtc(Btc btc) {
            this.btc = btc;
        }

    }

    public class Quote {

        @SerializedName("USD")
        @Expose
        private Usd usd;

        public Usd getUsd() {
            return usd;
        }

        public void setUsd(Usd usd) {
            this.usd = usd;
        }

    }

    public class Status {

        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("error_code")
        @Expose
        private Integer errorCode;
        @SerializedName("error_message")
        @Expose
        private Object errorMessage;
        @SerializedName("elapsed")
        @Expose
        private Integer elapsed;
        @SerializedName("credit_count")
        @Expose
        private Integer creditCount;
        @SerializedName("notice")
        @Expose
        private Object notice;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public Object getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(Object errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Integer getElapsed() {
            return elapsed;
        }

        public void setElapsed(Integer elapsed) {
            this.elapsed = elapsed;
        }

        public Integer getCreditCount() {
            return creditCount;
        }

        public void setCreditCount(Integer creditCount) {
            this.creditCount = creditCount;
        }

        public Object getNotice() {
            return notice;
        }

        public void setNotice(Object notice) {
            this.notice = notice;
        }
    }

    public class Usd {

        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("volume_24h")
        @Expose
        private Double volume24h;
        @SerializedName("volume_change_24h")
        @Expose
        private Double volumeChange24h;
        @SerializedName("percent_change_1h")
        @Expose
        private Double percentChange1h;
        @SerializedName("percent_change_24h")
        @Expose
        private Double percentChange24h;
        @SerializedName("percent_change_7d")
        @Expose
        private Double percentChange7d;
        @SerializedName("percent_change_30d")
        @Expose
        private Double percentChange30d;
        @SerializedName("percent_change_60d")
        @Expose
        private Double percentChange60d;
        @SerializedName("percent_change_90d")
        @Expose
        private Double percentChange90d;
        @SerializedName("market_cap")
        @Expose
        private Double marketCap;
        @SerializedName("market_cap_dominance")
        @Expose
        private Double marketCapDominance;
        @SerializedName("fully_diluted_market_cap")
        @Expose
        private Double fullyDilutedMarketCap;
        @SerializedName("last_updated")
        @Expose
        private String lastUpdated;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getVolume24h() {
            return volume24h;
        }

        public void setVolume24h(Double volume24h) {
            this.volume24h = volume24h;
        }

        public Double getVolumeChange24h() {
            return volumeChange24h;
        }

        public void setVolumeChange24h(Double volumeChange24h) {
            this.volumeChange24h = volumeChange24h;
        }

        public Double getPercentChange1h() {
            return percentChange1h;
        }

        public void setPercentChange1h(Double percentChange1h) {
            this.percentChange1h = percentChange1h;
        }

        public Double getPercentChange24h() {
            return percentChange24h;
        }

        public void setPercentChange24h(Double percentChange24h) {
            this.percentChange24h = percentChange24h;
        }

        public Double getPercentChange7d() {
            return percentChange7d;
        }

        public void setPercentChange7d(Double percentChange7d) {
            this.percentChange7d = percentChange7d;
        }

        public Double getPercentChange30d() {
            return percentChange30d;
        }

        public void setPercentChange30d(Double percentChange30d) {
            this.percentChange30d = percentChange30d;
        }

        public Double getPercentChange60d() {
            return percentChange60d;
        }

        public void setPercentChange60d(Double percentChange60d) {
            this.percentChange60d = percentChange60d;
        }

        public Double getPercentChange90d() {
            return percentChange90d;
        }

        public void setPercentChange90d(Double percentChange90d) {
            this.percentChange90d = percentChange90d;
        }

        public Double getMarketCap() {
            return marketCap;
        }

        public void setMarketCap(Double marketCap) {
            this.marketCap = marketCap;
        }

        public Double getMarketCapDominance() {
            return marketCapDominance;
        }

        public void setMarketCapDominance(Double marketCapDominance) {
            this.marketCapDominance = marketCapDominance;
        }

        public Double getFullyDilutedMarketCap() {
            return fullyDilutedMarketCap;
        }

        public void setFullyDilutedMarketCap(Double fullyDilutedMarketCap) {
            this.fullyDilutedMarketCap = fullyDilutedMarketCap;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

    }
}
package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import net.alureon.ircbutt.util.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a way to get the current spot price of BTC.
 */
public final class CryptocurrencyCommand implements Command {

    /**
     * The logger for this class.
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * How many coins to show for '!top' command.
     */
    private static final int TOP_COINS = 10;
    /**
     * This puts a limit on the amount of coins the bot can report in !top.
     */
    private static final int MAX_OUTPUT_IN_TOP = 10;
    /**
     * The number of extra static characters to pad name with for !top output.
     */
    private static final int TOP_NAME_FORMATTING_CHARS = 8;
    /**
     * The number of extra static characters to pad rank with in !top output.
     */
    private static final int TOP_RANK_FORMATTING_CHARS = 12;
    /**
     * The number of extra static characters to pad market cap with in !top output.
     */
    private static final int TOP_MARKET_CAP_FORMATTING_CHARS = 21;
    /**
     * The root URL for the CoinMarketCap API endpoint.
     */
    private static final String MARKETCAP_ENDPOINT = "https://pro-api.coinmarketcap.com/v1/";

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        String url = "";
        if (cmd[0].startsWith("top") || (cmd[0].equals("party")) || (cmd[0].equals("dump"))) {
            url = MARKETCAP_ENDPOINT + "cryptocurrency/listings/latest";
        } else {
            for (String x : this.getCommandAliases()) {
                if (cmd[0].equalsIgnoreCase(x)) {
                    url = MARKETCAP_ENDPOINT + "quotes/latest/?symbol=" + x;
                    break;
                }
            } 
        }
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(bufferedReader);
            Type currencyType = new TypeToken<List<CoinMarketCapResponse>>() {
            }.getType();
            List<CoinMarketCapResponse> currency = new Gson().fromJson(jsonText, currencyType);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            if (cmd[0].equals("top")) {
                if (event instanceof MessageEvent) {
                    MessageEvent messageEvent = (MessageEvent) event;
                    if (cmd.length == 1) {
                        handleTop(currency, nf, messageEvent, 0, TOP_COINS);
                    } else if (cmd.length == 3) {
                        try {
                            int min = Integer.parseInt(cmd[1]);
                            int max = Integer.parseInt(cmd[2]);
                            if (max - min > MAX_OUTPUT_IN_TOP) {
                                return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(),
                                        "you tryin to get butt kicked for spam!?!?!");
                            }
                            handleTop(currency, nf, messageEvent, min, max);
                        } catch (NumberFormatException ex) {
                            return new BotResponse(BotIntention.HIGHLIGHT, event.getUser(), "!top <min> <max>");
                        }
                    }
                }
                return null;
            }
            if (cmd[0].equals("party") || cmd[0].equals("dump")) {
                return handleParty(currency, nf, cmd[0]);
            }
            if (!cmd[0].endsWith("v")) {
                return formatCoinRequest(currency, nf);
            } else {
                return new BotResponse(BotIntention.CHAT, null, String.valueOf(currency.get(0).getData().getBtc().getQuote().getUsd().getPrice()));
            }
        } catch (IOException ex) {
            log.error("Error handling CoinMarketCap request: " + ex.getMessage());
            return new BotResponse(BotIntention.NO_REPLY, null, null);
        }
    }

    /**
     * Handles the formatting for the bot's coin request.
     *
     * @param currency The list of currencies retrieved from the coinmarketcap API.
     * @param nf       The NumberFormat instance for formatting currencies.
     * @return The bot's formatted response.
     */
    private BotResponse formatCoinRequest(final List<CoinMarketCapResponse> currency, final NumberFormat nf) {
        String dayChange = getColoredChangeText(currency.get(0).getData().getBtc().getQuote().getUsd().getPercentChange24h());
        String hourChange = getColoredChangeText(currency.get(0).getData().getBtc().getQuote().getUsd().getPercentChange1h());
        String weekChange = getColoredChangeText(currency.get(0).getData().getBtc().getQuote().getUsd().getPercentChange7d());

        if (currency.get(0).getData().getBtc().getQuote().getUsd().getMarketCap() != null) {

            return new BotResponse(BotIntention.CHAT, null, Colors.CYAN + Colors.BOLD
                    + currency.get(0).getData().getBtc().getName() + Colors.NORMAL + Colors.TEAL + ": "
                    + nf.format(currency.get(0).getData().getBtc().getQuote().getUsd().getPrice()) + " | Rank: "
                    + currency.get(0).getData().getBtc().getCmcRank() + Colors.TEAL + " | Market Cap: "
                    + nf.format(currency.get(0).getData().getBtc().getQuote().getUsd().getMarketCap()),
                    Colors.TEAL
                            + "[Hour " + hourChange + "] | [Day " + dayChange + "] | [Week " + weekChange + "]");
        } else {
            return new BotResponse(BotIntention.CHAT, null, Colors.CYAN + Colors.BOLD
                    + currency.get(0).getData().getBtc().getName() + Colors.NORMAL + Colors.TEAL + ": "
                    + nf.format(Double.valueOf(currency.get(0).getData().getBtc().getQuote().getUsd().getPrice()))
                    + " | Rank: " + currency.get(0).getData().getBtc().getCmcRank()
                    + " | [" + dayChange + "] ",
                    "Market Cap: N/A");
        }
    }

    /**
     * Convenience method for getting colored change text depending on positive or negative change.
     *
     * @param change The change for whatever duration of time, be it positive or negative.
     * @return The formatted string with colors.
     */
    private String getColoredChangeText(final double change) {
    if (change < 0) {
            return Colors.RED + change + "%" + Colors.NORMAL + Colors.TEAL;
        }
        return Colors.GREEN + "+" + change + "%" + Colors.NORMAL + Colors.TEAL;
    }

    /**
     * Handles the partying coin command.
     *
     * @param currencies The list of currencies from CoinMarketCap.
     * @param nf The NumberFormat instance for formatting currencies.
     * @param cmd The command issued, either "party" or "dump".
     * @return the bot's response.
     */
    private BotResponse handleParty(final List<CoinMarketCapResponse> currencies, final NumberFormat nf,
                                    final String cmd) {
        double result = 0;
        CoinMarketCapResponse partyCoin = null;
        for (CoinMarketCapResponse x : currencies) {
            try {
                double change = x.getData().getBtc().getQuote().getUsd().getPercentChange24h();
                if (cmd.equals("party") && change > result) {
                        partyCoin = x;
                } else if (cmd.equals("dump") && change < result) {
                        partyCoin = x;
                }
            } catch (NumberFormatException ex) {
                log.warn("Failed to parse change data: " + x.getData().getBtc().getQuote().getUsd().getPercentChange24h());
            }
        }
        String color;
        if (cmd.equals("party")) {
            color = Colors.MAGENTA;
        } else {
            color = Colors.BROWN;
        }
        if (partyCoin != null) {
            return new BotResponse(BotIntention.CHAT, null, color + Colors.BOLD
                    + partyCoin.getData().getBtc().getName() + Colors.NORMAL + Colors.TEAL + ": "
                    + nf.format(partyCoin.getData().getBtc().getQuote().getUsd().getPrice()) + " | Rank: "
                    + partyCoin.getData().getBtc().getCmcRank() + Colors.TEAL + " | Market Cap: "
                    + nf.format(Double.valueOf(partyCoin.getData().getBtc().getQuote().getUsd().getMarketCap())),
                    Colors.TEAL
                            + "[Hour " + getColoredChangeText(partyCoin.getData().getBtc().getQuote().getUsd().getPercentChange1h()) + "] | "
                            + "[Day " + getColoredChangeText(partyCoin.getData().getBtc().getQuote().getUsd().getVolume24h()) + "]");
        } else {
            log.error("Failed to find a partying coin!");
            return new BotResponse(BotIntention.NO_REPLY, null, null);
        }
    }

    /**
     * Handles the top coins command portion.
     *
     * @param currency the list of top currencies from coinmarketcap
     * @param nf       The numberformat instance for formatting currency
     * @param event    The MessageEvent (for sending channel several messages)
     * @param min      The point in the marketcap rank to start at.
     * @param max      The point in the marketcap rank to finish at.
     */
    private void handleTop(final List<CoinMarketCapResponse> currency, final NumberFormat nf,
                           final MessageEvent event, final int min, final int max) {
        int paddingName = 0;
        int paddingPrice = 0;
        int paddingRank = 0;
        int paddingMarketCap = 0;
        int paddingHourChange = 0;
        int paddingDayChange = 0;
        int paddingWeekChange = 0;
        for (int i = min; i < max; i++) {
            if (currency.get(i).getData().getBtc().getName().length() > paddingName) {
                paddingName = currency.get(i).getData().getBtc().getName().length();
            }
            if (nf.format(Double.valueOf(currency.get(i).getData().getBtc().getQuote().getUsd().getPrice())).length() > paddingPrice) {
                paddingPrice = nf.format(Double.valueOf(currency.get(i).getData().getBtc().getQuote().getUsd().getPrice())).length();
            }
            if (String.valueOf(currency.get(i).getData().getBtc().getCmcRank()).length() > paddingRank) {
                paddingRank = String.valueOf(currency.get(i).getData().getBtc().getCmcRank()).length();
            }
            if (String.valueOf(currency.get(i).getData().getBtc().getQuote().getUsd().getMarketCap()).length() > paddingMarketCap) {
                paddingMarketCap = String.valueOf(currency.get(i).getData().getBtc().getQuote().getUsd().getMarketCap()).length();
            }
            if ((" | [Hour " + getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange1h()) + "]").length()
                    > paddingHourChange) {
                paddingHourChange = (" | [Hour "
                        + getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange1h()) + "]").length();
            }
            if ((" | [Day " + getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange24h()) + "]").length()
                    > paddingDayChange) {
                paddingDayChange = (" | [Day "
                        + getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange24h()) + "]").length();
            }
            if ((" | [Week " + getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange7d()) + "]").length()
                    > paddingWeekChange) {
                paddingWeekChange = (" | [Week "
                        + getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange7d()) + "]").length();
            }
        }
        paddingName += TOP_NAME_FORMATTING_CHARS;
        paddingRank += TOP_RANK_FORMATTING_CHARS;
        paddingMarketCap += TOP_MARKET_CAP_FORMATTING_CHARS;
        log.debug("Name padding: " + paddingName);
        log.debug("Value padding: " + paddingPrice);
        log.debug("Rank padding: " + paddingRank);
        log.debug("MarketCap padding: " + paddingMarketCap);
        log.debug("1h padding: " + paddingHourChange);
        log.debug("1d padding: " + paddingDayChange);
        log.debug("1w padding: " + paddingWeekChange);
        for (int i = min; i < max; i++) {
            String hourChange = getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange1h());
            String dayChange = getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange24h());
            String weekChange = getColoredChangeText(currency.get(i).getData().getBtc().getQuote().getUsd().getPercentChange7d());
            String message =
                    // name
                    StringUtils.rightPad(Colors.CYAN + Colors.BOLD + currency.get(i).getData().getBtc().getName() + Colors.NORMAL
                            + Colors.TEAL, paddingName)
                            // price
                            + " | " // no need to be padded
                            + StringUtils.leftPad(nf.format(Double.valueOf(currency.get(i).getData().getBtc().getQuote().getUsd().getPrice())),
                            paddingPrice)
                            // rank
                            + StringUtils.rightPad(" | Rank: " + currency.get(i).getData().getBtc().getCmcRank() + Colors.TEAL, paddingRank)
                            // market cap
                            + StringUtils.rightPad(" | Market Cap: "
                            + nf.format(Double.valueOf(currency.get(i).getData().getBtc().getQuote().getUsd().getMarketCap())), paddingMarketCap)
                            // hourly change
                            + StringUtils.leftPad(" | [Hour " + hourChange + "]", paddingHourChange)
                            // daily change
                            + StringUtils.leftPad(" | [Day " + dayChange + "]", paddingDayChange)
                            // weekly change
                            + StringUtils.leftPad(" | [Week " + weekChange + "]", paddingWeekChange);
            IRCUtils.sendChannelMessage(event.getChannel(), message);
        }
    }

    /**
     * Returns all String data from provided Reader.
     *
     * @param rd - The reader to read from
     * @return String - A concatenated String of all data
     * @throws IOException Throws IOException if unable to read JSON
     */
    private static String readAll(final Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("btc", "ltc", "eth", "btcv", "ltcv", "ethv", "vtc", "vtcv", "xrp", "xrpv",
                "bch", "bchv", "dash", "dashv", "iota", "iotav", "wtc", "wtcv", "ada", "adav", "xem", "xemv", "btg",
                "btgv", "xmr", "xmrv", "eos", "eosv", "xlm", "xlmv", "zec", "zecv", "usdt", "usdtv", "steem", "steemv",
                "doge", "dogev", "bnb", "bnbv", "gnt", "gntv", "etc", "etcv", "neo", "neov", "ppt", "pptv", "bcc",
                "bccv", "qtum", "qtumv", "waves", "wavesv", "trx", "trxv", "xvg", "xvgv", "icx", "icxv", "poe",
                "poev", "aion", "aionv", "fc2", "fc2v", "cnd", "cndv", "put", "putv", "trump", "trumpv", "mana",
                "manav", "lsk", "lskv", "tnt", "tntv", "fuel", "fuelv", "zrx", "zrxv", "dbg", "dbgv", "fun", "funv",
                "ethos", "ethosv", "pot", "potv", "1st", "1stv", "bts", "btsv", "omg", "omgv", "ardr", "ardrv", "strat",
                "stratv", "top", "party", "dump", "mdc", "mdcv"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

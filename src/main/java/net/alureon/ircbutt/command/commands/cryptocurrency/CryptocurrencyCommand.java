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

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        String url = "";
        if (cmd[0].startsWith("vtc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/vertcoin/";
        } else if (cmd[0].startsWith("btc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/bitcoin/";
        } else if (cmd[0].startsWith("eth")) {
            url = "https://api.coinmarketcap.com/v1/ticker/ethereum/";
        } else if (cmd[0].startsWith("ltc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/litecoin/";
        } else if (cmd[0].startsWith("wtc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/walton/";
        } else if (cmd[0].startsWith("xrp")) {
            url = "https://api.coinmarketcap.com/v1/ticker/ripple/";
        } else if (cmd[0].startsWith("bch")) {
            url = "https://api.coinmarketcap.com/v1/ticker/bitcoin-cash/";
        } else if (cmd[0].startsWith("dash")) {
            url = "https://api.coinmarketcap.com/v1/ticker/dash/";
        } else if (cmd[0].startsWith("iota")) {
            url = "https://api.coinmarketcap.com/v1/ticker/iota/";
        } else if (cmd[0].startsWith("ada")) {
            url = "https://api.coinmarketcap.com/v1/ticker/cardano/";
        } else if (cmd[0].startsWith("xem")) {
            url = "https://api.coinmarketcap.com/v1/ticker/nem/";
        } else if (cmd[0].startsWith("btg")) {
            url = "https://api.coinmarketcap.com/v1/ticker/bitcoin-gold/";
        } else if (cmd[0].startsWith("xmr")) {
            url = "https://api.coinmarketcap.com/v1/ticker/monero/";
        } else if (cmd[0].startsWith("eos")) {
            url = "https://api.coinmarketcap.com/v1/ticker/eos/";
        } else if (cmd[0].startsWith("xlm")) {
            url = "https://api.coinmarketcap.com/v1/ticker/stellar/";
        } else if (cmd[0].startsWith("zec")) {
            url = "https://api.coinmarketcap.com/v1/ticker/zcash/";
        } else if (cmd[0].startsWith("usdt")) {
            url = "https://api.coinmarketcap.com/v1/ticker/tether/";
        } else if (cmd[0].startsWith("steem")) {
            url = "https://api.coinmarketcap.com/v1/ticker/steem/";
        } else if (cmd[0].startsWith("doge")) {
            url = "https://api.coinmarketcap.com/v1/ticker/dogecoin/";
        } else if (cmd[0].startsWith("bnb")) {
            url = "https://api.coinmarketcap.com/v1/ticker/binance-coin/";
        } else if (cmd[0].startsWith("gnt")) {
            url = "https://api.coinmarketcap.com/v1/ticker/golem/";
        } else if (cmd[0].startsWith("etc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/ethereum-classic/";
        } else if (cmd[0].startsWith("neo")) {
            url = "https://api.coinmarketcap.com/v1/ticker/neo/";
        } else if (cmd[0].startsWith("ppt")) {
            url = "https://api.coinmarketcap.com/v1/ticker/populous/";
        } else if (cmd[0].startsWith("bcc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/bitconnect/";
        } else if (cmd[0].startsWith("qtum")) {
            url = "https://api.coinmarketcap.com/v1/ticker/qtum/";
        } else if (cmd[0].startsWith("waves")) {
            url = "https://api.coinmarketcap.com/v1/ticker/waves/";
        } else if (cmd[0].startsWith("trx")) {
            url = "https://api.coinmarketcap.com/v1/ticker/tron/";
        } else if (cmd[0].startsWith("xvg")) {
            url = "https://api.coinmarketcap.com/v1/ticker/verge/";
        } else if (cmd[0].startsWith("icx")) {
            url = "https://api.coinmarketcap.com/v1/ticker/icon/";
        } else if (cmd[0].startsWith("poe")) {
            url = "https://api.coinmarketcap.com/v1/ticker/poet/";
        } else if (cmd[0].startsWith("aion")) {
            url = "https://api.coinmarketcap.com/v1/ticker/aion/";
        } else if (cmd[0].startsWith("fc2")) {
            url = "https://api.coinmarketcap.com/v1/ticker/fuelcoin/";
        } else if (cmd[0].startsWith("cnd")) {
            url = "https://api.coinmarketcap.com/v1/ticker/Cindicator/";
        } else if (cmd[0].startsWith("put")) {
            url = "https://api.coinmarketcap.com/v1/ticker/putincoin/";
        } else if (cmd[0].startsWith("trump")) {
            url = "https://api.coinmarketcap.com/v1/ticker/trumpcoin/";
        } else if (cmd[0].startsWith("mana")) {
            url = "https://api.coinmarketcap.com/v1/ticker/Decentraland/";
        } else if (cmd[0].startsWith("lsk")) {
            url = "https://api.coinmarketcap.com/v1/ticker/lisk/";
        } else if (cmd[0].startsWith("tnt")) {
            url = "https://api.coinmarketcap.com/v1/ticker/Tierion/";
        } else if (cmd[0].startsWith("fuel")) {
            url = "https://api.coinmarketcap.com/v1/ticker/Etherparty/";
        } else if (cmd[0].startsWith("zrx")) {
            url = "https://api.coinmarketcap.com/v1/ticker/0x/";
        } else if (cmd[0].startsWith("dgb")) {
            url = "https://api.coinmarketcap.com/v1/ticker/digibyte/";
        } else if (cmd[0].startsWith("fun")) {
            url = "https://api.coinmarketcap.com/v1/ticker/funfair/";
        } else if (cmd[0].startsWith("ethos")) {
            url = "https://api.coinmarketcap.com/v1/ticker/ethos/";
        } else if (cmd[0].startsWith("pot")) {
            url = "https://api.coinmarketcap.com/v1/ticker/potcoin/";
        } else if (cmd[0].startsWith("1st")) {
            url = "https://api.coinmarketcap.com/v1/ticker/firstblood/";
        } else if (cmd[0].startsWith("bts")) {
            url = "https://coinmarketcap.com/currencies/bitshares/";
        } else if (cmd[0].startsWith("omg")) {
            url = "https://api.coinmarketcap.com/v1/ticker/omisego/";
        } else if (cmd[0].startsWith("ardr")) {
            url = "https://api.coinmarketcap.com/v1/ticker/ardor/";
        } else if (cmd[0].startsWith("mdc")) {
            url = "https://api.coinmarketcap.com/v1/ticker/madcoin/";
        } else if (cmd[0].startsWith("strat")) {
            url = "https://api.coinmarketcap.com/v1/ticker/stratis/";
        } else if (cmd[0].startsWith("top") || (cmd[0].equals("party")) || (cmd[0].equals("dump"))) {
            url = "https://api.coinmarketcap.com/v1/ticker/";
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
                return new BotResponse(BotIntention.CHAT, null, currency.get(0).getPriceUsd());
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
        String dayChange = getColoredChangeText(currency.get(0).getPercentChange24h());
        String hourChange = getColoredChangeText(currency.get(0).getPercentChange1h());
        String weekChange = getColoredChangeText(currency.get(0).getPercentChange7d());

        if (currency.get(0).getMarketCapUsd() != null) {

            return new BotResponse(BotIntention.CHAT, null, Colors.CYAN + Colors.BOLD
                    + currency.get(0).getName() + Colors.NORMAL + Colors.TEAL + ": "
                    + nf.format(Double.valueOf(currency.get(0).getPriceUsd())) + " | Rank: "
                    + currency.get(0).getRank() + Colors.TEAL + " | Market Cap: "
                    + nf.format(Double.valueOf(currency.get(0).getMarketCapUsd())),
                    Colors.TEAL
                            + "[Hour " + hourChange + "] | [Day " + dayChange + "] | [Week " + weekChange + "]");
        } else {
            return new BotResponse(BotIntention.CHAT, null, Colors.CYAN + Colors.BOLD
                    + currency.get(0).getName() + Colors.NORMAL + Colors.TEAL + ": "
                    + nf.format(Double.valueOf(currency.get(0).getPriceUsd()))
                    + " | Rank: " + currency.get(0).getRank()
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
    private String getColoredChangeText(final String change) {
        if (change.startsWith("-")) {
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
                double change = Double.parseDouble(x.getPercentChange24h());
                if (cmd.equals("party") && change > result) {
                        partyCoin = x;
                } else if (cmd.equals("dump") && change < result) {
                        partyCoin = x;
                }
            } catch (NumberFormatException ex) {
                log.warn("Failed to parse change data: " + x.getPercentChange24h());
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
                    + partyCoin.getName() + Colors.NORMAL + Colors.TEAL + ": "
                    + nf.format(Double.valueOf(partyCoin.getPriceUsd())) + " | Rank: "
                    + partyCoin.getRank() + Colors.TEAL + " | Market Cap: "
                    + nf.format(Double.valueOf(partyCoin.getMarketCapUsd())),
                    Colors.TEAL
                            + "[Hour " + getColoredChangeText(partyCoin.getPercentChange1h()) + "] | "
                            + "[Day " + getColoredChangeText(partyCoin.getPercentChange24h()) + "]");
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
            if (currency.get(i).getName().length() > paddingName) {
                paddingName = currency.get(i).getName().length();
            }
            if (nf.format(Double.valueOf(currency.get(i).getPriceUsd())).length() > paddingPrice) {
                paddingPrice = nf.format(Double.valueOf(currency.get(i).getPriceUsd())).length();
            }
            if (currency.get(i).getRank().length() > paddingRank) {
                paddingRank = currency.get(i).getRank().length();
            }
            if (currency.get(i).getMarketCapUsd().length() > paddingMarketCap) {
                paddingMarketCap = currency.get(i).getMarketCapUsd().length();
            }
            if ((" | [Hour " + getColoredChangeText(currency.get(i).getPercentChange1h()) + "]").length()
                    > paddingHourChange) {
                paddingHourChange = (" | [Hour "
                        + getColoredChangeText(currency.get(i).getPercentChange1h()) + "]").length();
            }
            if ((" | [Day " + getColoredChangeText(currency.get(i).getPercentChange24h()) + "]").length()
                    > paddingDayChange) {
                paddingDayChange = (" | [Day "
                        + getColoredChangeText(currency.get(i).getPercentChange24h()) + "]").length();
            }
            if ((" | [Week " + getColoredChangeText(currency.get(i).getPercentChange7d()) + "]").length()
                    > paddingWeekChange) {
                paddingWeekChange = (" | [Week "
                        + getColoredChangeText(currency.get(i).getPercentChange7d()) + "]").length();
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
            String hourChange = getColoredChangeText(currency.get(i).getPercentChange1h());
            String dayChange = getColoredChangeText(currency.get(i).getPercentChange24h());
            String weekChange = getColoredChangeText(currency.get(i).getPercentChange7d());
            String message =
                    // name
                    StringUtils.rightPad(Colors.CYAN + Colors.BOLD + currency.get(i).getName() + Colors.NORMAL
                            + Colors.TEAL, paddingName)
                            // price
                            + " | " // no need to be padded
                            + StringUtils.leftPad(nf.format(Double.valueOf(currency.get(i).getPriceUsd())),
                            paddingPrice)
                            // rank
                            + StringUtils.rightPad(" | Rank: " + currency.get(i).getRank() + Colors.TEAL, paddingRank)
                            // market cap
                            + StringUtils.rightPad(" | Market Cap: "
                            + nf.format(Double.valueOf(currency.get(i).getMarketCapUsd())), paddingMarketCap)
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

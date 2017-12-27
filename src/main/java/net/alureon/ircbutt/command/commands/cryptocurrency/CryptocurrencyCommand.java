package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.Colors;
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
        }
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(bufferedReader);
            Type currencyType = new TypeToken<List<CoinMarketCapResponse>>() {
            }.getType();
            List<CoinMarketCapResponse> currency = new Gson().fromJson(jsonText, currencyType);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            if (!cmd[0].endsWith("v")) {
                String change;
                if (currency.get(0).getPercentChange24h().startsWith("-")) {
                    change = Colors.RED + currency.get(0).getPercentChange24h() + "%" + Colors.NORMAL + Colors.TEAL;
                } else {
                    change = Colors.GREEN + currency.get(0).getPercentChange24h() + "%" + Colors.NORMAL + Colors.TEAL;
                }
                if (currency.get(0).getMarketCapUsd() != null) {

                    return new BotResponse(BotIntention.CHAT, null, Colors.CYAN + Colors.BOLD
                            + currency.get(0).getName() + Colors.NORMAL + Colors.TEAL + ": "
                            + nf.format(Double.valueOf(currency.get(0).getPriceUsd())) + " | Rank: "
                            + currency.get(0).getRank(), Colors.TEAL + "Market Cap: "
                            + nf.format(Double.valueOf(currency.get(0).getMarketCapUsd()))
                            + " | [" + change + "] ");
                } else {
                    return new BotResponse(BotIntention.CHAT, null, Colors.CYAN + Colors.BOLD
                            + currency.get(0).getName() + Colors.NORMAL + Colors.TEAL + ": "
                            + nf.format(Double.valueOf(currency.get(0).getPriceUsd()))
                            + " | Rank: " + currency.get(0).getRank()
                            + " | [" + change + "] ",
                            "Market Cap: N/A");

                }
            } else {
                return new BotResponse(BotIntention.CHAT, null, currency.get(0).getPriceUsd());
            }
        } catch (IOException ex) {
            log.error("Error handling CoinMarketCap request: " + ex.getMessage());
            return new BotResponse(BotIntention.NO_REPLY, null, null);
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
                "manav", "lsk", "lskv", "tnt", "tntv", "fuel", "fuelv"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

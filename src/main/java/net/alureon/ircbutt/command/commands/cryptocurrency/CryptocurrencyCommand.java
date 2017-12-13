package net.alureon.ircbutt.command.commands.cryptocurrency;

import com.google.gson.Gson;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.command.Command;
import net.alureon.ircbutt.response.BotIntention;
import net.alureon.ircbutt.response.BotResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

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
        Class cryptocurrency = null;
        if (cmd[0].startsWith("btc")) {
            url = "https://api.coinbase.com/v2/prices/BTC-USD/spot";
            cryptocurrency = BTC.class;
        } else if (cmd[0].startsWith("ltc")) {
            url = "https://api.coinbase.com/v2/prices/LTC-USD/spot";
            cryptocurrency = LTC.class;
        } else if (cmd[0].startsWith("eth")) {
            url = "https://api.coinbase.com/v2/prices/ETH-USD/spot";
            cryptocurrency = ETH.class;
        } else if (cmd[0].startsWith("vtc") || cmd[0].startsWith("xrp")) {
            return handleCoinMarketRequest(cmd[0], "vertcoin");
        }
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(bufferedReader);
            Gson gson = new Gson();
            Cryptocurrency currency = gson.fromJson(jsonText, (Type) cryptocurrency);
            if (!cmd[0].endsWith("v")) {
                return new BotResponse(BotIntention.CHAT, null, currency.getData().getBase()
                        + ": " + currency.getData().getAmount() + " " + currency.getData().getCurrency());
            } else {
                return new BotResponse(BotIntention.CHAT, null, currency.getData().getAmount());
            }
        } catch (IOException e) {
            log.error("Could not get market status! ", e.getMessage());
        }
        return null;
}

    /**
     * Handles a request to CoinMarketCap's API as opposed to GDAX.
     * Very few cryptocurrencyies are supported by GDAX/Coinbase currently.
     * @param command The command that was passed (may be a value request).
     * @param coinName The name of the coin (via API) to retrieve.
     * @return The bot's response (the ticker price)
     */
    private BotResponse handleCoinMarketRequest(final String command, final String coinName) {
        String url = "";
        if (coinName.equalsIgnoreCase("vertcoin")) {
            url = "https://api.coinmarketcap.com/v1/ticker/vertcoin/";
        } else if (coinName.equalsIgnoreCase("ripple")) {
            url = "";
        } else if (coinName.equalsIgnoreCase("bch")) {
            url = "";
        }
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(bufferedReader);
            System.out.println(jsonText);
            Gson gson = new Gson();
            CoinMarketCapResponse currency = gson.fromJson(jsonText, CoinMarketCapResponse.class);
            if (!command.endsWith("v")) {
                return new BotResponse(BotIntention.CHAT, null, currency.getName()
                        + ": " + currency.getPriceUsd() + " [" + currency.getPercentChange24h() + "]");
            } else {
                return new BotResponse(BotIntention.CHAT, null, currency.getPriceUsd());
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
        return new ArrayList<>(Arrays.asList("btc", "ltc", "eth", "btcv", "ltcv", "ethv", "vtc", "vtcv"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

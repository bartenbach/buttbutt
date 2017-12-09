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
        return new ArrayList<>(Arrays.asList("btc", "ltc", "eth", "btcv", "ltcv", "ethv"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        return false;
    }
}

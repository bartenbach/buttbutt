package net.alureon.ircbutt.handler.command;

import com.google.gson.Gson;
import net.alureon.ircbutt.BotResponse;
import net.alureon.ircbutt.IRCbutt;
import net.alureon.ircbutt.util.GoogleResults;
import net.alureon.ircbutt.util.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by alureon on 1/31/15.
 */

public class GoogleSearchHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(GoogleSearchHandler.class);


    public GoogleSearchHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void handleGoogleSearch(BotResponse response, User user, String[] cmd) {
        butt.getMoreHandler().clearMore();
        if (cmd.length > 1) {
            GoogleResults results = getMyGoogHoles(response, StringUtils.getArgs(cmd));
            assert results != null;
            String title = results.getResponseData().getResults().get(0).getTitle().replaceAll("\\<.*?\\>", "");
            String url = results.getResponseData().getResults().get(0).getUrl();
            String title2 = results.getResponseData().getResults().get(1).getTitle().replace("\\<.*?\\>", "");
            String url2 = results.getResponseData().getResults().get(1).getUrl();
            String title3 = results.getResponseData().getResults().get(2).getTitle().replace("\\<.*?\\>", "");
            String url3 = results.getResponseData().getResults().get(2).getUrl();
            try {
                response.chat(URLDecoder.decode(StringEscapeUtils.unescapeHtml4(title), "utf-8"), URLDecoder.decode(url, "utf8"));
                butt.getMoreHandler().setMore(URLDecoder.decode(StringEscapeUtils.unescapeHtml4(title2), "utf-8") + URLDecoder.decode(url2, "utf-8"));
                butt.getMoreHandler().setMore2(URLDecoder.decode(StringEscapeUtils.unescapeHtml4(title3), "utf-8") + URLDecoder.decode(url3, "utf-8"));
                butt.getMoreHandler().setMore3("google it yourself fuckwad");
            } catch (UnsupportedEncodingException ex) {
                log.error("Failed decoding URL ", ex);
            }
        } else {
            response.privateMessage(user, "!g <search term>");
        }
    }

    public GoogleResults getMyGoogHoles(BotResponse response, String query) {
        final String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        final String charset = "UTF-8";
        URL url = null;

        try {
            url = new URL(google + URLEncoder.encode(query, charset));
            Reader reader = null;
            try {
                reader = new InputStreamReader(url.openStream(), charset);
                return new Gson().fromJson(reader, GoogleResults.class);
            } catch (IOException e) {
                e.printStackTrace();
                response.noResponse();
            }
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
            response.noResponse();
        }
        return null;
    }

}

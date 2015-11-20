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
import java.util.List;

/**
 * Created by alureon on 1/31/15.
 */

public class GoogleSearchHandler {


    private IRCbutt butt;
    final static Logger log = LoggerFactory.getLogger(GoogleSearchHandler.class);


    public GoogleSearchHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public static void handleGoogleSearch(IRCbutt butt, BotResponse response, User user, String[] cmd) {
        butt.getMoreHandler().clearMore();
        if (cmd.length > 1) {
            String search = StringUtils.getArgs(cmd);
            GoogleResults results = getMyGoogHoles(response, search);
            assert results != null;
            List<GoogleResults.Result> resultList = results.getResponseData().getResults();
            int size = resultList.size();
            if (size > 0) {
                try {
                    for (int i = 0; i < size; i++) {
                        GoogleResults.Result result = resultList.get(i);
                        String title = result.getTitle().replaceAll("\\<.*?\\>", "");
                        String url = result.getUrl();
                        String text1 = URLDecoder.decode(StringEscapeUtils.unescapeHtml4(title), "utf-8");
                        String text2 = URLDecoder.decode(url, "utf8");
                        if (i == 0) {
                            response.chat(text1, text2);
                        } else {
                            butt.getMoreHandler().addMore(text1 + text2);
                        }
                    }
                    butt.getMoreHandler().setNoMore("https://google.com/search?q=" + URLEncoder.encode(search, "utf8"));
                } catch (UnsupportedEncodingException ex) {
                    log.error("Failed decoding URL ", ex);
                }
            } else {
                response.privateMessage(user, "i found nothing");
            }

        } else {
            response.privateMessage(user, "!g <search term>");
        }
    }

    public static GoogleResults getMyGoogHoles(BotResponse response, String query) {
        final String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        final String charset = "UTF-8";

        try {
            URL url = new URL(google + URLEncoder.encode(query, charset));
            try {
                Reader reader = new InputStreamReader(url.openStream(), charset);
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

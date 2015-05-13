package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by alureon on 3/1/15.
 */

public class WeatherHandler {

    final static Logger log = LoggerFactory.getLogger(WeatherHandler.class);

    public void getFuckingWeather(BotResponse response, String place) {
        StringBuilder sb = new StringBuilder();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

        try {
            Document doc = Jsoup.connect("http://thefuckingweather.com/?where=" + place).userAgent(userAgent).get();
            Elements remark = doc.getElementsByClass("topRemark");
            Elements flavor = doc.getElementsByClass("remark");
            sb.append(remark.first().text())
                    .append(".  ")
                    .append(flavor.first().text());
            response.chat(sb.toString());
        } catch (IOException | NullPointerException ex) {
            log.error("Failed to get the fucking weather", ex);
            response.privateMessage(response.getRecipient(), "couldn't get the fucking weather for " + place);
        }
    }

}

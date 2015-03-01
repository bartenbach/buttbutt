package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.BotResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by alureon on 3/1/15.
 */

public class WeatherHandler {

    public void getFuckingWeather(BotResponse response, String place) {
        StringBuilder sb = new StringBuilder();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

        try {
            Document doc = Jsoup.connect("http://thefuckingweather.com/?where=" + place).userAgent(userAgent).get();
            Element location = doc.getElementById("locationDisplaySpan");
            Elements temperature = doc.getElementsByClass("temperature");
            Elements remark = doc.getElementsByClass("remark");
            sb.append(location.text()).append(" Weather: ").append(temperature.first().text()).append("°F ").append(remark.first().text());
            response.chat(sb.toString());
        } catch (IOException ex) {
            response.chat("couldn't get the fucking weather");
        }
    }

}

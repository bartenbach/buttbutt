package net.alureon.ircbutt.handler;

import com.google.gson.Gson;
import net.alureon.ircbutt.util.GoogleResults;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by alureon on 1/31/15.
 */
public class GoogleSearch {

    public static GoogleResults getMyGoogHoles(String query) {
        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String charset = "UTF-8";

        URL url = null;
        try {
            url = new URL(google + URLEncoder.encode(query, charset));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(url.openStream(), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
        return results;
    }

}

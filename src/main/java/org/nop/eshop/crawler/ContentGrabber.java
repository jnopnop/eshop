package org.nop.eshop.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ContentGrabber {

    public static Document getDocument(String url) {
        StringBuffer tmp = new StringBuffer();
        try {
            URL _url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)_url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jsoup.parse(tmp.toString());
    }
}

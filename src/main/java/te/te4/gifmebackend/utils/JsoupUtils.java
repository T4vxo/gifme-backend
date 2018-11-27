/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.utils;

import com.google.common.hash.Hashing;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Some Jsoup utilities.
 *
 * @author Johan Svensson
 */
public abstract class JsoupUtils {

    private JsoupUtils() {

    }

    private static String getExternalJsProcessorKey() {
        SimpleDateFormat f = new SimpleDateFormat("dd");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        return Hashing.sha256().hashString(
                String.format("8c6614bcda13617a410f44430769c1cd%s", f.format(new Date())),
                StandardCharsets.UTF_8
        ).toString().toUpperCase();
    }

    public static Element random(Elements from) {
        return from.get((int) Math.floor(from.size() * Math.random()));
    }
    
    public static Document getDocument(String url) throws IOException {
        return getDocument(url, false);
    }

    /**
     * @return A document after JS has loaded the page.
     */
    public static Document getDocument(String url, boolean requireJsProcessing) throws IOException {
        //  Use an external JS processor
        
        if (!requireJsProcessing) {
            //  Just use native parsing
            return Jsoup.parse(new URL(url), 30000);
        }
        
        return Jsoup.parse(HttpUtils.getResponseEntity(
                String.format("http://localhost:8034/%s?key=%s",
                        URLEncoder.encode(url, "UTF8"),
                        getExternalJsProcessorKey()
                )
        ));
    }
}

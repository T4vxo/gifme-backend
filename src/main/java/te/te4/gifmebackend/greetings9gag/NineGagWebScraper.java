/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import te.te4.gifmebackend.greetings9gag.models.Gag;
import te.te4.gifmebackend.utils.HttpUtils;
import te.te4.gifmebackend.utils.JsoupUtils;

/**
 * Scapes the official 9GAG site using JSoup.
 *
 * @author Johan Svensson
 */
public class NineGagWebScraper {

    public static final String URL_HOT = "https://9gag.com/hot";
    public static final String URL_GAG = "https://9gag.com/gag/%s";

    private Document document;
    private ArrayList<Gag> gags;

    /**
     * Fetches the 9GAG site and parses HTML.
     */
    public NineGagWebScraper() throws Exception {
        scrapeListItems(HttpUtils.getResponseEntity(URL_HOT));
    }

    private void scrapeListItems(String fullInnerHtml) throws Exception {
        //  Used to find the JSON with list items
        String objectStart = "GAG.App.loadConfigs(";
        int objectStartIndexOf = fullInnerHtml.indexOf(objectStart);

        if (objectStartIndexOf == -1) {
            throw new Exception("Malformed 9GAG HTML source.");
        }

        String objectTerminator = ").loadAsynScripts";
        String listItemsJson = fullInnerHtml.substring(
                objectStartIndexOf + objectStart.length(),
                fullInnerHtml.indexOf(objectTerminator, objectStartIndexOf)
        );

        JSONArray postsSource = JSONObject.parseObject(listItemsJson)
                .getJSONObject("data").getJSONArray("posts");

        gags = new ArrayList<>();
        for (int i = 0, n = postsSource.size(); i < n; i++) {
            JSONObject postSource = postsSource.getJSONObject(i);
            gags.add(new Gag(
                    postSource.getString("title"),
                    postSource.getString("id"),
                    postSource.getJSONObject("images").getJSONObject("image700").getString("url")
            ));
        }
    }

    public Gag getRandomGag() {
        return gags.get((int) Math.floor(gags.size() * Math.random()));
    }
}

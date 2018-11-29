/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.memebuilder;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import te.te4.gifmebackend.utils.HttpUtils;

/**
 *
 * @author Administratör
 */
public class GetWikiApi {
    public static List<String> GetWiki(String sek) throws IOException{
        //hämtar api
        JSONObject res = HttpUtils.getResponseJson("https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=" + sek);
        //stopar in de två första resultaten i en lista
        List<String> result = new ArrayList<>();
        result.add(res.getJSONObject("query").getJSONArray("search").getJSONObject(0).getString("title"));
        result.add(res.getJSONObject("query").getJSONArray("search").getJSONObject(1).getString("title"));
        return result;
    }
}

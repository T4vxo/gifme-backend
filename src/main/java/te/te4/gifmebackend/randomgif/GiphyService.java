/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.randomgif;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.apache.http.client.utils.URIBuilder;
import te.te4.gifmebackend.utils.HttpUtils;

/**
 *
 * @author ElKebabHenry
 */
@Stateless
public class GiphyService {

    private static GiphyService instance = new GiphyService();
// Singelton of giphy service
    public static GiphyService getInstance() {
        return instance;
    }

    public String getGifUrl(String searchTag) throws IOException {

        String apiKey = "l84tUqqgu4FKD2gCV2KKlxc4yxXqaRIM";

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.giphy.com")
                    .setPath("/v1/gifs/random")
                    .setParameter("api_key", apiKey)
                    .setParameter("tag", searchTag)
                    .build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(GiphyService.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject result = HttpUtils.getResponseJson(uri.toString());
        return result
                .getJSONObject("data")
                .getJSONObject("images")
                .getJSONObject("original_still")
                .getString("url");
    }
}

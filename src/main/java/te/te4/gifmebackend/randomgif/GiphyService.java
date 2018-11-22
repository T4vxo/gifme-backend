/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.randomgif;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author ElKebabHenry
 */
public class GiphyService {

    public void GipfyCon() {
        
        String apiKey = "l84tUqqgu4FKD2gCV2KKlxc4yxXqaRIM";
        String searchTag = "toast";
        
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

        HttpClient hc = HttpClientBuilder.create().build();
        HttpGet hg = new HttpGet(uri);
       //hc.execute(hg).getEntity().getContent();
    }
}

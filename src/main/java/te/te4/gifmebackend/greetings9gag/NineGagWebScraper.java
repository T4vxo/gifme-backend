/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import te.te4.gifmebackend.greetings9gag.models.Gag;
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

    /**
     * Fetches the 9GAG site and parses HTML.
     */
    public NineGagWebScraper() throws IOException {
        document = JsoupUtils.getDocument(URL_HOT);
        System.out.println("Document HTML: " + document.html());
    }

    public Gag getRandomGag() throws IOException {
        Elements streams = document.select("[id^=\"stream-\"]");
        Elements articles = document.select("[id^=\"jsid-post-\"]");
    
        System.out.print("Streams: " ); 
        System.out.println(streams);
        
        Element chosenArticle = JsoupUtils.random(articles);
        
        return getGagFromArticle(chosenArticle);
    }
    
    private Gag getGagFromArticle(Element article) throws IOException {
        Element entryId = article.select("a[data-entry-id]").first();
        return getGagFromGagPage(entryId.attr("data-entry-id"));
    }
        
    private Gag getGagFromGagPage(String gagId) throws IOException {
        Document page = Jsoup.connect(String.format(URL_GAG, gagId)).get();
        
        Elements comments = page.getElementsByClass("comment-entry");
        Element chosenComment = JsoupUtils.random(comments);
        
        String contentString;
        
        do {
            contentString = chosenComment.getElementsByClass("content")
                    .first().html().trim();
        } while (contentString.length() == 0);
        
        Element image = page.getElementsByClass("image-post").first();
        String imageUrl = image.getElementsByTag("img").first().attr("src");
        
        return new Gag(contentString, gagId, imageUrl);
    }
}

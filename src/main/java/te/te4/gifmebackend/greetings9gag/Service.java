/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag;

import java.util.List;
import te.te4.gifmebackend.greetings9gag.models.Gag;
import te.te4.gifmebackend.memebuilder.GetWikiApi;
import te.te4.gifmebackend.utils.Utils;

/**
 * Staeless service for the Greetings, 9GAG! request handler.
 *
 * @author Johan Svensson
 */
public class Service {

    private static Service instance = new Service();

    public static Service getInstance() {
        return instance;
    }

    private Service() {

    }

    /**
     * @return A result with a fetched displayed text.
     */
    public Gag getRandomResult() {
        try {
            Gag result = new NineGagWebScraper().getRandomGag();

            String[] keywordCandidates = result.title.split(" ");
            String keyword = null;

            do {
                keyword = result.titleKeyword = Utils.random(keywordCandidates);
                List<String> displayedTextList = GetWikiApi.GetWiki(keyword);
                String[] displayedText = result.displayedText = displayedTextList.toArray(
                        new String[displayedTextList.size()]
                );

                break;

            } while (true);

            return result;

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}

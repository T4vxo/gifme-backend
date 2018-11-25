/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag;

import te.te4.gifmebackend.greetings9gag.models.Gag;

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

    public Gag getRandomResult() {
        try {
            return new NineGagWebScraper().getRandomGag();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}

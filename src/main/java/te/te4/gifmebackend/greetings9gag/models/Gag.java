/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag.models;

import jdk.nashorn.internal.runtime.NumberToString;

/**
 * Represents a gag to be fetched from 9GAG shown to the user.
 * @author Administratör
 */
public class Gag {
    public String title;
    /**
     * Keyword from the title to use with the text fetching API.
     */
    public String titleKeyword;
    /**
     * Text fetched from API.
     */
    public String[] displayedText;
    public String gagId;
    public String imageUrl; 

    public Gag(String title, String gagId, String imageUrl) {
        this.title = title;
        this.gagId = gagId;
        this.imageUrl = imageUrl;
    }
    
    @Override
    public String toString() {
        return String.format("[id: %s, title: %s, url: %s]", gagId, title, imageUrl);
    }
}

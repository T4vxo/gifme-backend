/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag.models;

/**
 * Represents a gag to be fetched from 9GAG shown to the user.
 * @author Administratör
 */
public class Gag {
    public String comment;
    public String gagId;
    public String imageUrl;

    public Gag(String comment, String gagId, String imageUrl) {
        this.comment = comment;
        this.gagId = gagId;
        this.imageUrl = imageUrl;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import java.util.HashMap;

/**
 *
 * @author johan
 */
public class SuperSecret {
    private static SuperSecret instance = new SuperSecret();
    public static SuperSecret getInstance() {
        return instance;
    }
    
    private HashMap<String, String> credentials;

    public SuperSecret() {
        credentials = new HashMap<>();
        credentials.put("client_id", "f9eb926c89f1a44655a3");
        credentials.put("client_secret", "65b7e4dc91baa311e5e535ace947cf5fed2ef4bb");
    }
    
    public String getCredential(String key) {
        return credentials.get(key);
    }
}

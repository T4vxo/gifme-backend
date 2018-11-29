/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import te.te4.gifmebackend.utils.HttpUtils;

/**
 *
 * @author johan
 */
public class Service {
    private static Service instance = new Service();

    public static Service getInstance() {
        return instance;
    }
    
    /**
     * Invokes #authWithSignUpFallback but uses an access token which is fetched
     * and originates from a client code.
     * @see #authWithSignUpFallback
     */
    public User authWithCodeAndSignUpFallback(String code) {
        try {
            SuperSecret secret = SuperSecret.getInstance();
            JSONObject authResult = HttpUtils.getResponseJson(
                    String.format("https://github.com/login/oauth/access_token" +
                            "?client_id=%s&client_secret=%s&code=%s",
                            secret.getCredential("client_id"),
                            secret.getCredential("client_secret"),
                            code),
                    HttpUtils.Method.POST,
                    null
            );
            
            System.out.println("Auth result: " + authResult.toJSONString());
            
            String accessToken = authResult.getString("access_token");
            User user = authWithSignUpFallback(accessToken);
            
            return user;
            
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Performs an auth request with GitHub and retrieves user data. If the auth
     * succeeded but there is no user record in the database, a record for the
     * authorized user is created.
     * @return User if successfully signed in, or null if internal error or unauthorized user.
     */
    public User authWithSignUpFallback(String accessToken) {
        try {
            JSONObject authResult = HttpUtils.getResponseJson(
                    String.format("https://api.github.com/user?access_token=%s", accessToken)
            );
            
            System.out.println("authResult: " + authResult.toJSONString());
            
            User user = new User();
            user.setAccessToken(accessToken);
            
            return user;
        } catch (IOException e) {
            return null;
        }
    }
}

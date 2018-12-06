/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import java.sql.Connection;
import java.util.Base64;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import te.te4.gifmebackend.db.ConnectionFactory;
import te.te4.gifmebackend.utils.PasswordAuthentication;

/**
 *
 * @author johan
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthTest {

    private static String adminAccessToken;

    @Test
    public void _001hashPasswordTest() {
        System.out.println("hashPasswordTest");
        String password = "hellote4";
        String hashed = PasswordAuthentication.getInstance().hash(
                password.toCharArray()
        );
        System.out.println("Hashed password: \"" + hashed + "\"");
    }

    @Test
    public void _002basicAuthTest() throws Exception {
        System.out.println("basicAuthTest");

        String password = "hellote4";
        String user = "admin";

        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(
                String.format("%s:%s", user, password).getBytes()
        );
        int userId = User.getUserIdFromAuth(basicAuthHeader);
        int expectedUserId = 1;

        Assert.assertEquals("Admin sign in with correct user Id", expectedUserId, userId);
    }

    @Test
    public void _003saveAccessTokenTest() throws Exception {
        System.out.println("saveAccessTokenTest");

        int adminUserId = 1;

        String accessToken = User.generateAndSaveAccessToken(adminUserId);
        adminAccessToken = accessToken;
        
        System.out.println("saveAccessTokenTest adminAccessToken: " + adminAccessToken);
        
        Assert.assertEquals(
                "Access token length should match constant",
                User.ACCESS_TOKEN_LENGTH, accessToken.length()
        );
    }

    @Test
    public void _004bearerAuthTest() throws Exception {
        System.out.println("bearerAuthTest");

        int adminUserId = 1;
        String accessToken = adminAccessToken;

        String bearerAuthHeader = "Bearer " + accessToken;
        
        System.out.println("bearerAuthTest header: " + bearerAuthHeader);

        int resultedUserId = User.getUserIdFromAuth(bearerAuthHeader);

        Assert.assertEquals(
                String.format("Admin user id from header: %s", bearerAuthHeader),
                adminUserId,
                resultedUserId
        );
    }
}

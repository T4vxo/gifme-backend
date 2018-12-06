/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import te.te4.gifmebackend.db.ConnectionFactory;
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
     *
     * @see #authWithSignUpFallback
     */
    public User authWithCodeAndSignUpFallback(String code) throws IllegalArgumentException, SQLException {
        try {
            SuperSecret secret = SuperSecret.getInstance();
            JSONObject authResult = HttpUtils.getResponseJson(
                    String.format("https://github.com/login/oauth/access_token"
                            + "?client_id=%s&client_secret=%s&code=%s",
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
        } catch (IllegalArgumentException illegalArgsEx) {
            throw illegalArgsEx;
        }
    }

    /**
     * Performs an auth request with GitHub and retrieves user data. If the auth
     * succeeded but there is no user record in the database, a record for the
     * authorized user is created.
     *
     * @return User if successfully signed in, or null if internal error or
     * unauthorized user.
     */
    public User authWithSignUpFallback(String accessToken) throws IllegalArgumentException, SQLException {
        try {
            JSONObject authResult = HttpUtils.getResponseJson(
                    String.format("https://api.github.com/user?access_token=%s", accessToken)
            );

            System.out.println("authResult: " + authResult.toJSONString());

            if ("Bad credentials".equalsIgnoreCase(authResult.getString("message"))) {
                //  Bad credentials
                throw new IllegalArgumentException("Bad credentials");
            }

            User user = new User();
            try {
                int userId = insertUser(authResult.getString("login"), "");
                user.setId(userId);
            } catch (SQLException e) {
                throw e;
            }

            String authToken = User.generateAndSaveAccessToken(user.getId());
            user.setAuthToken(authToken);

            return user;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Inserts a new user if it does not already exist.
     *
     * @return The numeric ID of the new user or the ID of an existing user.
     */
    public int insertUser(String username, String secret) throws SQLException {
        Connection connection = ConnectionFactory.createConnection();

        PreparedStatement existingUserStatement = connection.prepareStatement(
                "SELECT id FROM users WHERE username=?"
        );
        existingUserStatement.setString(1, username);
        ResultSet existingUserResult = existingUserStatement.executeQuery();
        if (existingUserResult.next()) {
            return existingUserResult.getInt("id");
        }

        //  Does not exist; insert
        PreparedStatement insertUserStatement = connection.prepareStatement(
                "INSERT INTO users (username, auth_secret) VALUES(?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );

        insertUserStatement.setString(1, username);
        insertUserStatement.setString(2, secret);

        insertUserStatement.executeUpdate();

        ResultSet generatedKeys = insertUserStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getInt(0);
        } else {
            throw new SQLException("No ID for user obtained");
        }
    }
}

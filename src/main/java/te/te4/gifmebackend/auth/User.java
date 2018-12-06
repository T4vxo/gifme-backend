/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import te.te4.gifmebackend.db.ConnectionFactory;
import te.te4.gifmebackend.utils.PasswordAuthentication;

/**
 * Represents a single user.
 *
 * @author johan
 */
public class User {

    public static int ACCESS_TOKEN_LENGTH = 32;

    /**
     * Uses some credentials to fetch a user's ID.
     *
     * @param authHeader Value from the Authorization header.
     * @throws Exception if the credentials are invalid.
     * @return The resolved user ID from the auth credentials.
     */
    public static int getUserIdFromAuth(String header) throws SecurityException, SQLException, IllegalStateException {
        header = header.trim();

        String[] fields = header.split(" ");

        if (fields.length < 2) {
            //  Insufficient fields
            throw new SecurityException("Less than two fields");
        }

        if (fields[0].equals("Bearer")) {
            //  Check auth token

            String token = fields[1];

            try (Connection connection = ConnectionFactory.createConnection()) {

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id FROM users WHERE auth_token=?"
                );
                statement.setString(1, token);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return result.getInt("id");
                } else {
                    throw new SecurityException("Invalid token credentials");
                }

            } catch (SQLException ex) {
                throw ex;
            }
        } else if (fields[0].equals("Basic")) {
            //  Check auth token
            String[] credentials = new String(
                    Base64.getDecoder().decode(fields[1])
            ).split(":");

            if (credentials.length < 2) {
                throw new SecurityException("Basic auth misses password");
            }

            String username = credentials[0], password = credentials[1];

            try (Connection connection = ConnectionFactory.createConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, auth_secret FROM users WHERE username=?"
                );
                statement.setString(1, username);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    String hashedPassword = result.getString("auth_secret");

                    try {
                        if (!PasswordAuthentication.getInstance().authenticate(
                                password.toCharArray(), hashedPassword)) {
                            //  Invalid password
                            throw new SecurityException("Invalid basic credentials");
                        }
                    } catch (Exception ex) {
                        throw new IllegalStateException(ex.getMessage());
                    }

                    //  Authorized
                    return result.getInt("id");
                } else {
                    throw new SecurityException("Invalid basic credentials");
                }

            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            throw new SecurityException("Unknown authorization type: " + fields[0]);
        }
    }

    /**
     * @return An unique access token string with 32 in length
     */
    public static String generateAccessToken() throws SQLException {
        char[] allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder builder = new StringBuilder();

        Connection connection = ConnectionFactory.createConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT 'exists' FROM users WHERE auth_token=?"
        );

        while (true) {
            for (int i = 0, n = ACCESS_TOKEN_LENGTH; i < n; i++) {
                builder.append(
                        allowedChars[(int) Math.floor(allowedChars.length * Math.random())]
                );
            }
            statement.setString(1, builder.toString());
            
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                //  Is unique
                break;
            }
            
            builder.setLength(0);
        }

        return builder.toString();
    }

    public static String generateAndSaveAccessToken(int userId) throws SQLException {
        String accessToken = generateAccessToken();

        Connection connection = ConnectionFactory.createConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET auth_token=? WHERE id=?"
        );

        statement.setString(1, accessToken);
        statement.setInt(2, userId);
        statement.executeUpdate();

        return accessToken;
    }

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

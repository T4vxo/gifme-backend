/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import te.te4.gifmebackend.auth.SuperSecret;

/**
 * Establishes connection instances to the default database.
 * @author johan
 */
public class ConnectionFactory {
    private static String CONFIG_HOST = "192.168.0.4";
    
    /**
     * Initializes a new connection using default connection args.
     * @return An established connection to the database.
     */
    public static Connection createConnection() throws SQLException {
        String host = String.format("jdbc:mysql://%s", CONFIG_HOST);
        String db = "gifme";
        
        SuperSecret secret = SuperSecret.getInstance();
        String user = secret.getCredential("db_user");
        String password = secret.getCredential("db_password");
        
        return DriverManager.getConnection(
                String.format("%s/%s", host, db),
                user,
                password
        );
    }
}

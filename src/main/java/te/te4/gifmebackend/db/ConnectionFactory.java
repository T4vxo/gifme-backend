/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author johan
 */
public class ConnectionFactory {
    /**
     * Initializes a new connection using default connection args.
     */
    public static Connection createConnection() {
        String host = "jdbc:mysql://localhost/";
        String db = null;
        //DriverManager.getConnection(, string1, string2)
        return null;
    }
}

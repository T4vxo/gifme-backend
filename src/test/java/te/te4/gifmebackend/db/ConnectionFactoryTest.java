/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johan
 */
public class ConnectionFactoryTest {
    
    public ConnectionFactoryTest() {
    }

    /**
     * Test of createConnection method, of class ConnectionFactory.
     */
    @org.junit.Test
    public void testCreateConnection() throws SQLException {
        System.out.println("createConnection");
        ConnectionFactory.createConnection();
    }
    
}

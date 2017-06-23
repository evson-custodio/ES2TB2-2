/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evson
 */
public abstract class JDBC {
    private static final Map<String, Connection> CONNECTIONS = new HashMap<>();
    
    public static Connection getConnection(String url) {
        return CONNECTIONS.get(url);
    }
    
    public static Connection getConnection(String driver, String url, String user, String password) {
        Connection connection = CONNECTIONS.get(url);
        
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                CONNECTIONS.put(url, connection);
            }
            catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return connection;
    }
}

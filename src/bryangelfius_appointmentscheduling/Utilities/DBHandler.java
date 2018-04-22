/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bgelfius
 */
public class DBHandler {
    private static final String dbdriver     = "com.mysql.jdbc.Driver";
    private static final String dbname     = "dbname";
//    private static final String dburl      = "jdbc:mysql://localhost/" + dbname;
    private static final String dburl      = "jdbc:mysql://url/" + dbname;
    private static final String dbuser     = "dbuser";
    private static final String dbpass     = "dbpass";
    
    // db connection
    private static Connection dbconn = null;
    private static Boolean currentlyLoggedIn;
    private static String currentUser;
    
    
    public DBHandler() {
        
    }
    
    public static Connection getCurrentConnection(){
        return dbconn;
    }
    
    
    public static void open() throws ClassNotFoundException, SQLException {
        try {
            // TODO
            // init database connection
            Class.forName(dbdriver);
            dbconn = DriverManager.getConnection(dburl,dbuser,dbpass);          
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
            throw ex;
        } 
        
    }  
    
    public static void close() throws SQLException {
        try {
            if (dbconn != null && !dbconn.isClosed()) {
                dbconn.close();
            }
        } catch (SQLException ex){
           System.out.println(ex.toString());
           throw ex;
        }
    }
    
    
}

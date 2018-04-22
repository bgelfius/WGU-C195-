/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import bryangelfius_appointmentscheduling.View_Controller.LoginController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bgelfius
 */
public class UserDAO {
    private static User currUser;
    
    public UserDAO () {
        
    }
    
    public static User getCurrUser() {
        return currUser;
    }
    
    public static void logoutCurrentUser() {
        currUser.setLoggedInStatus(Boolean.TRUE);
    }
    
    public static void loadUser(String username) throws SQLException {
        try {
            currUser = new User();
            
            String query = "SELECT * "
                    + "FROM U04C2c.user  "
                    + " WHERE username = '" + username + "' ";
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
                
               currUser.setUserID(rs.getInt("userid"));
               currUser.setUserName(rs.getString("userName"));
               if (rs.getInt("active") == 1) {
                currUser.setActive(Boolean.TRUE);
               } else { 
                currUser.setActive(Boolean.FALSE);
               }
               currUser.setCreateDate(rs.getString("createDate"));
               currUser.setCreatedBy(rs.getString("createBy"));
               currUser.setLastUpdate(rs.getString("lastUpdate"));
               currUser.setLastUpdateBy(rs.getString("lastUpdatedBy"));
             
            }
    
            st.close();
            
            } catch (SQLException ex) {
                throw ex;
        }
        
    }
    
    public static Boolean validateUser(String username, String password) {
        Boolean found = false;
        
        try {
            String query = "SELECT userName, password "
                    + "FROM U04C2c.user "
                    + "WHERE userName = '" + username + "' and password = '" + password + "' ";
            
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query); 

        // if this ID already exists, we quit
        if(rs.absolute(1)) {
            found = true;
        }
            
        loadUser(username);
        currUser.setLoggedInStatus(Boolean.TRUE);
        
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return found;
    }
}

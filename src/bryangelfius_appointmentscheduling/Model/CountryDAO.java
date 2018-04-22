/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import bryangelfius_appointmentscheduling.View_Controller.MainAppointmentController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bgelfius
 */
public class CountryDAO {
       private static Country currentCountry = new Country();
    
    
    public static Country getCurrentCountry(){
        return currentCountry;
    }
    
    public static int getCountryByName(String countryname) throws SQLException {
        try {
            String query = "SELECT * "
                    + "FROM U04C2c.country  "
                    + " WHERE country = '" + countryname + "' ";
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
               currentCountry.setCountryID(rs.getInt("countryId"));
               currentCountry.setCountry(rs.getString("country"));
               currentCountry.setCreateDate(rs.getString("createDate"));
               currentCountry.setCreatedBy(rs.getString("createdBy"));
               currentCountry.setLastUpdate(rs.getString("lastUpdate"));
               currentCountry.setLastUpdatedBy(rs.getString("lastUpdateBy"));
             }
            
            st.close();
            
                  
            
        } catch (SQLException ex) {
            throw ex;
        }
           
        return currentCountry.getCountryID();
        
    }
    
    
    public static ObservableList<Country> getCountries() throws SQLException {
 
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * "
                    + "FROM U04C2c.country c "
                    + "ORDER BY country ";
                    
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
             Country c;
    
            while (rs.next()) {
              c = new Country();
              c.setCountryID(rs.getInt("countryId"));
              c.setCountry(rs.getString("country"));
              c.setCreateDate(rs.getString("createDate"));
              c.setCreatedBy(rs.getString("createdBy"));
              c.setLastUpdate(rs.getString("lastUpdate"));
              c.setLastUpdatedBy(rs.getString("lastUpdateBy"));
                
              countryList.add(c);
            }
            
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MainAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return countryList;
    }
    
    
    public static void loadCountry(int countryID) throws SQLException {
        try {
            String query = "SELECT * "
                    + "FROM U04C2c.country  "
                    + " WHERE countryId = " + countryID;
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
              currentCountry.setCountryID(rs.getInt("countryId"));
              currentCountry.setCountry(rs.getString("country"));
              currentCountry.setCreateDate(rs.getString("createDate"));
              currentCountry.setCreatedBy(rs.getString("createdBy"));
              currentCountry.setLastUpdate(rs.getString("lastUpdate"));
              currentCountry.setLastUpdatedBy(rs.getString("lastUpdateBy"));
             }
            
            st.close();
            
                  
            
        } catch (SQLException ex) {
            throw ex;
        }
     
    }
    
    public CountryDAO(){
        
    } 
}

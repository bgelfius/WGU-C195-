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
public class CityDAO {
    private static City currentCity = new City();
    
    
    public static City getCurrentCity(){
        return currentCity;
    }
    
    public static int getCityByName(String cityname) throws SQLException {
        try {
            String query = "SELECT * "
                    + "FROM U04C2c.city  "
                    + " WHERE city = '" + cityname + "' ";
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
               currentCity.setCityID(rs.getInt("cityId"));
               currentCity.setCity(rs.getString("city"));
               currentCity.setCountryID(rs.getInt("countryId"));
               currentCity.setCreateDate(rs.getString("createDate"));
               currentCity.setCreatedBy(rs.getString("createdBy"));
               currentCity.setLastUpdate(rs.getString("lastUpdate"));
               currentCity.setLastUpdatedBy(rs.getString("lastUpdateBy"));
             }
            
            st.close();
            
                  
            
        } catch (SQLException ex) {
            throw ex;
        }
           
        return currentCity.getCityID();
        
    }
    
    
    public static ObservableList<City> getCities() throws SQLException {
 
        ObservableList<City> cityList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * "
                    + "FROM U04C2c.city c "
                    + "ORDER BY city ";
                    
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
             City c;
    
            while (rs.next()) {
              c = new City();
              c.setCityID(rs.getInt("cityid"));
              c.setCity(rs.getString("city"));
              c.setCountryID(rs.getInt("countryid"));
              c.setCreateDate(rs.getString("createDate"));
              c.setCreatedBy(rs.getString("createdBy"));
              c.setLastUpdate(rs.getString("lastUpdate"));
              c.setLastUpdatedBy(rs.getString("lastUpdateBy"));
                
              cityList.add(c);
            }
            
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MainAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return cityList;
    }
    
    
    public static void loadCity(int cityID) throws SQLException {
        try {
            String query = "SELECT * "
                    + "FROM U04C2c.city  "
                    + " WHERE cityid = " + cityID;
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
               currentCity.setCityID(rs.getInt("cityId"));
               currentCity.setCity(rs.getString("city"));
               currentCity.setCountryID(rs.getInt("countryId"));
               currentCity.setCreateDate(rs.getString("createDate"));
               currentCity.setCreatedBy(rs.getString("createdBy"));
               currentCity.setLastUpdate(rs.getString("lastUpdate"));
               currentCity.setLastUpdatedBy(rs.getString("lastUpdateBy"));
             }
            
            st.close();
            
                  
            
        } catch (SQLException ex) {
            throw ex;
        }
     
    }
    
    public CityDAO(){
        
    }
}

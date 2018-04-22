/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author bgelfius
 */
public class AddressDAO {
    private static Address currentAddress = new Address();
    
    
    public static Address getCurrentAddress() {
        return currentAddress;
    }
    
    public static void loadAddress(int addressID) throws SQLException {
            try {
            String   query = "SELECT * "
                    + "FROM U04C2c.address  "
                    + " WHERE addressId = " + addressID;
            
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
         
            
          
            //build current address
            while (rs.next()) {
               currentAddress.setAddressID(rs.getInt("addressId"));
               currentAddress.setAddress(rs.getString("address"));
               currentAddress.setAddress2(rs.getString("address2"));
               currentAddress.setCityID(rs.getInt("cityId"));
               currentAddress.setPhone(rs.getString("phone"));
               currentAddress.setPostalCode(rs.getString("postalcode"));
               currentAddress.setCreateDate(rs.getString("createDate"));
               currentAddress.setCreatedBy(rs.getString("createdBy"));
               currentAddress.setLastUpdate(rs.getString("lastUpdate"));
               currentAddress.setLastUpdatedBy(rs.getString("lastUpdateBy"));
            }
    
            st.close();
            
                  
            
        } catch (SQLException ex) {
            throw ex;
        }
            
    }
    
    public static void save(Address addr) throws SQLException {
            
        String query =  "INSERT INTO `U04C2c`.`address`\n" +
                        "(`address`,\n" +
                        "`address2`,\n" +
                        "`cityId`,\n" +
                        "`postalCode`,\n" +
                        "`phone`,\n" +
                        "`createDate`,\n" +
                        "`createdBy`,\n" +
                        "`lastUpdate`,\n" +
                        "`lastUpdateBy`)\n" +
                        "VALUES\n" +
                        "('" + addr.getAddress() + "',\n" +
                        "'" + addr.getAddress2() + "',\n" +
                        " " + addr.getCityID() + ",\n" +
                        " '" + addr.getPostalCode()+ "',\n" +
                        " '" + addr.getPhone() + "',\n" +
                        " CURRENT_TIMESTAMP,\n" +
                        " '" + UserDAO.getCurrUser().getUserName() + "',\n" +
                        " CURRENT_TIMESTAMP,\n" +
                        " '" + UserDAO.getCurrUser().getUserName() + "');";
 
        //System.out.println(query);

        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        
        // inserted record now we need the key
        ResultSet generatedKeys = st.getGeneratedKeys();
        if (generatedKeys.next()) {
            loadAddress(generatedKeys.getInt(1));
        }
        

    }
    
    public static void update(Address addr) throws SQLException {
        
        String query =  "UPDATE `U04C2c`.`address`\n" +
                        "SET\n" +
                        "`address` = '" + addr.getAddress() + "',\n" +
                        "`address2` = '" + addr.getAddress2() + "',\n" +
                        "`cityId` = " + addr.getCityID() + ",\n" +
                        "`postalCode` = '" + addr.getPostalCode()+ "',\n" +
                        "`phone` = '" + addr.getPhone() + "',\n" +
                        "`createDate` = '" + addr.getCreateDate()+ "',\n" +
                        "`createdBy` = '" + addr.getCreatedBy()+ "',\n" +
                        "`lastUpdate` = CURRENT_TIMESTAMP,\n" +
                        "`lastUpdateBy` = '" + UserDAO.getCurrUser().getUserName() + "'\n" +
                        "WHERE `addressid` = " + addr.getAddressID(); 

        System.out.println(query);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query);
        
    }
    
    public static void delete(int addressID) throws SQLException {
        
        String query =  "DELETE FROM U04C2c.address  "
                    + " WHERE addressId = " + addressID + ";";
        

        System.out.println(query);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query);
        
    }
    public AddressDAO(){
        
    }
}

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bgelfius
 */
public class CustomerDAO {
    private static Customer currCustomer = new Customer();
    
    public static Customer getCurrentCustomer() {
        return currCustomer;
    }
    public static ObservableList<Customer>  getAllCustomers() {
        ObservableList<Customer> custList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * "
                    + "FROM U04C2c.customer ";
 
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            while (rs.next()) {
                Customer cust = new Customer(rs.getInt("customerid"),
                rs.getString("customerName"),
                rs.getInt("addressId"),
                rs.getBoolean("active"),
                rs.getString("createDate"),
                rs.getString("createdBy"),
                rs.getString("lastUpdate"),
                rs.getString("lastUpdateBy")
                );
                
                custList.add(cust);
                
             }
    
            st.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return custList;
    }
    
    public static void loadCustomer(int customerID) throws SQLException {
            try {
            String query = "SELECT c.* "
                    + "FROM U04C2c.customer c "
                    + " WHERE c.customerid = " + customerID;
 
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
                
               currCustomer.setCustomerID(rs.getInt("customerId"));
               currCustomer.setAddressID(rs.getInt("addressId"));
               if (rs.getInt("active") == 1) {
                currCustomer.setActive(Boolean.TRUE);
               } else { 
                currCustomer.setActive(Boolean.FALSE);
               }
               currCustomer.setCustomerName(rs.getString("customerName"));
               currCustomer.setCreateDate(rs.getString("createDate"));
               currCustomer.setCreatedBy(rs.getString("createdBy"));
               currCustomer.setLastUpdate(rs.getString("lastUpdate"));
               currCustomer.setLastUpdatedBy(rs.getString("lastUpdateBy"));
             
            }
    
            st.close();
            
                    
        } catch (SQLException ex) {
            throw ex;
        }
            
    }
    
    public static void save(Customer cust) throws SQLException {
        String query = "INSERT INTO U04C2c.customer\n" +
                       "(`customerName`,\n" +
                       "`addressId`,\n" +
                       "`active`,\n" +
                       "`createDate`,\n" +
                       "`createdBy`,\n" +
                       "`lastUpdate`,\n" +
                       "`lastUpdateBy`)\n" +
                       "VALUES\n" +
                       "( '" + cust.getCustomerName() + "',\n" +
                       " " + cust.getAddressID() + ",\n" +
                       " " + cust.getActive() + ",\n" +
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
            loadCustomer(generatedKeys.getInt(1));
        }
    }
    
    public static void update(Customer cust) throws SQLException {
        String query = "UPDATE `U04C2c`.`customer`" +
                       "SET " +
                       "`customerName` = '" + currCustomer.getCustomerName() + "', " +
                       "`addressId` = " + currCustomer.getAddressID() + ", " +
                       "`active` = " + currCustomer.getActive() + ", " +
                       "`createDate` = '" + currCustomer.getCreateDate()+ "', " +
                       "`createdBy` = '" + currCustomer.getCreatedBy()+ "', " +
                       "`lastUpdate` = CURRENT_TIMESTAMP, " +
                       "`lastUpdateBy` = '" + UserDAO.getCurrUser().getUserName()  + "'" +
                       " WHERE `customerid` = "+ currCustomer.getCustomerID() + ";"; 

//         System.out.println(query);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query);
        
    }
    
    public static void delete(int customerID) throws SQLException {
        
        loadCustomer(customerID);
        
        // need to delete the FK first 
        AddressDAO.delete(currCustomer.getAddressID());
        
        String query =  "DELETE FROM U04C2c.customer  "
                      + " WHERE customerid = " + customerID + ";";

        System.out.println(query);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query);
        
    }
    
    public CustomerDAO () {
        
    }
    
    
}

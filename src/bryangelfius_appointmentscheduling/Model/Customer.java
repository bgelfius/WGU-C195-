/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bgelfius
 */
public class Customer {
    private final IntegerProperty customerID;
    private final StringProperty customerName;
    private final IntegerProperty addressID;
    private final BooleanProperty active;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdatedBy;
    
    private int rc;
    private ArrayList errorList;
    
    
    public Customer() {
        this.customerID  = new SimpleIntegerProperty();
        this.customerName  = new SimpleStringProperty();
        this.addressID  = new SimpleIntegerProperty();
        this.active  = new SimpleBooleanProperty();
        this.createDate  = new SimpleStringProperty();
        this.createdBy  = new SimpleStringProperty();
        this.lastUpdate  = new SimpleStringProperty();
        this.lastUpdatedBy  = new SimpleStringProperty();
  
    }
    
    public Customer(int customerID, String customerName, int addressID, Boolean active, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.customerID  = new SimpleIntegerProperty(customerID);
        this.customerName  = new SimpleStringProperty(customerName);
        this.addressID  = new SimpleIntegerProperty(addressID);
        this.active  = new SimpleBooleanProperty(active);
        this.createDate  = new SimpleStringProperty(createDate);
        this.createdBy  = new SimpleStringProperty(createdBy);
        this.lastUpdate  = new SimpleStringProperty(lastUpdate);
        this.lastUpdatedBy  = new SimpleStringProperty(lastUpdatedBy);
  
    }
    public IntegerProperty getCustomerIDProperty() {
        return this.customerID;
    }
    public StringProperty getCustomerNameProperty() {
        return this.customerName;
    }
    public IntegerProperty getAddressIDProperty() {
        return this.addressID;
    }
    public BooleanProperty getActiveProperty() {
        return this.active;
    }
    public StringProperty getCreateDateProperty() {
        return this.createDate;
    }
    public StringProperty getCreatedByProperty() {
        return this.createdBy;
    }
    public StringProperty getLastUpdateProperty() {
        return this.lastUpdate;
    }
    public StringProperty getLastUpdatedByProperty() {
        return this.lastUpdatedBy;
    }
      
    public  void setCustomerID(int id) {
        this.customerID.set(id);
    } 
    public  int getCustomerID() {
        return customerID.get();
    }
    public  void setCustomerName(String id) {
        this.customerName.set(id);
    } 
    public  String getCustomerName() {
        return customerName.get();
    }
    public  void setAddressID(int id) {
        this.addressID.set(id);
    } 
    public  int getAddressID() {
        return addressID.get();
    }
    public  void setActive(Boolean id) {
        this.active.set(id);
    } 
    public  Boolean getActive() {
        return active.get();
    }
    public  void setCreateDate(String id) {
        this.createDate.set(id);
    } 
    public  String getCreateDate() {
        return createDate.get();
    }
    public  void setCreatedBy(String id) {
        this.createdBy.set(id);
    } 
    public  String getCreatedBy() {
        return createdBy.get();
    }
    public  void setLastUpdate(String id) {
        this.lastUpdate.set(id);
    } 
    public  String getLastUpdate() {
        return lastUpdate.get();
    }
    public  void setLastUpdatedBy(String id) {
        this.lastUpdatedBy.set(id);
    } 
    public  String getLastUpdatedBy() {
        return lastUpdatedBy.get();
    }
     public int validateCustomer() {
                
        this.errorList = new ArrayList();
        this.rc = 0;
        
        if (this.getCustomerName().equals("")) {
            this.rc = -1;
            this.errorList.add("Customer name must be valued");
        }
                
        if (this.getAddressID() >0) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("Address must be provided");
        }
        
   
        return this.rc;
        
    }
 
    public ArrayList getErrors() {
        
        if (this.rc == -1 ){
            return this.errorList;
        } else {
            return null;
        }
    } 
       
    
}

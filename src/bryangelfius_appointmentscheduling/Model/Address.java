/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bgelfius
 */
public class Address {
    private final IntegerProperty addressID;
    private final StringProperty address;
    private final StringProperty address2;
    private final IntegerProperty cityID;
    private final StringProperty postalCode;
    private final StringProperty phone;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdatedBy;
    
    private int rc;
    private ArrayList errorList;
  
    public Address() {
        this.addressID = new SimpleIntegerProperty();
        this.address  = new SimpleStringProperty();
        this.address2  = new SimpleStringProperty();
        this.cityID  = new SimpleIntegerProperty();
        this.postalCode  = new SimpleStringProperty();
        this.phone  = new SimpleStringProperty();
        this.createDate  = new SimpleStringProperty();
        this.createdBy  = new SimpleStringProperty();
        this.lastUpdate  = new SimpleStringProperty();
        this.lastUpdatedBy  = new SimpleStringProperty();
    }
    public Address(int addressID, String address,String address2, int cityID, String postalCode, String phone, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.addressID = new SimpleIntegerProperty(addressID);
        this.address  = new SimpleStringProperty(address);
        this.address2  = new SimpleStringProperty(address2);
        this.cityID  = new SimpleIntegerProperty(cityID);
        this.postalCode  = new SimpleStringProperty(postalCode);
        this.phone  = new SimpleStringProperty(phone);
        this.createDate  = new SimpleStringProperty(createDate);
        this.createdBy  = new SimpleStringProperty(createdBy);
        this.lastUpdate  = new SimpleStringProperty(lastUpdate);
        this.lastUpdatedBy  = new SimpleStringProperty(lastUpdatedBy);
    }
       
    public IntegerProperty getAddressIDProperty() {
        return this.addressID;
    }
    public StringProperty getAddressProperty() {
        return this.address;
    }
    public StringProperty getAddress2Property() {
        return this.address2;
    }
    public IntegerProperty getCityIDProperty() {
        return this.cityID;
    }
    public StringProperty getPostalCodeProperty() {
        return this.postalCode;
    }
    public StringProperty getPhoneProperty() {
        return this.phone;
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

    public  void setAddressID(int id) {
        this.addressID.set(id);
    } 
    public  int getAddressID() {
        return addressID.get();
    }
    public  void setAddress(String id) {
        this.address.set(id);
    } 
    public  String getAddress() {
        return address.get();
    }
    public  void setAddress2(String id) {
        this.address2.set(id);
    } 
    public  String getAddress2() {
        return address2.get();
    }
    public  void setCityID(int id) {
        this.cityID.set(id);
    } 
    public  int getCityID() {
        return cityID.get();
    }
    public  void setPostalCode(String id) {
        this.postalCode.set(id);
    } 
    public  String getPostalCode() {
        return postalCode.get();
    }
    public  void setPhone(String id) {
        this.phone.set(id);
    } 
    public  String getPhone() {
        return phone.get();
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
    
    public int validateAddress() {
                
        this.errorList = new ArrayList();
        this.rc = 0;
        
        if (this.getAddress().equals("")) {
            this.rc = -1;
            this.errorList.add("Address must be valued");
        }
                
        if (this.getCityID() >0) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("City must be provided");
        }
        
         if (this.getPostalCode().equals("")) {
            this.rc = -1;
            this.errorList.add("Postal code must be valued");
        }
        
         if (this.getPhone().equals("")) {
            this.rc = -1;
            this.errorList.add("Phone number must be valued");
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

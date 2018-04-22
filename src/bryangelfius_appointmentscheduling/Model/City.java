/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bgelfius
 */
public class City {
    private final IntegerProperty cityID;
    private final StringProperty city;
    private final IntegerProperty countryID;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdatedBy;
    
    
    public City() {
        this.cityID = new SimpleIntegerProperty();
        this.city  = new SimpleStringProperty();
        this.countryID  = new SimpleIntegerProperty();
        this.createDate  = new SimpleStringProperty();
        this.createdBy  = new SimpleStringProperty();
        this.lastUpdate  = new SimpleStringProperty();
        this.lastUpdatedBy  = new SimpleStringProperty();
    }
    
       
    public IntegerProperty getAddressIDProperty() {
        return this.cityID;
    }
    public StringProperty getAddressProperty() {
        return this.city;
    }
    public IntegerProperty getCityIDProperty() {
        return this.countryID;
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

    public  void setCityID(int id) {
        this.cityID.set(id);
    } 
    public  int getCityID() {
        return cityID.get();
    }
    public  void setCity(String id) {
        this.city.set(id);
    } 
    public  String getCity() {
        return city.get();
    }
    public  void setCountryID(int id) {
        this.countryID.set(id);
    } 
    public  int getCountryID() {
        return countryID.get();
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
    
}

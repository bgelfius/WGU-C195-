/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

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
public class User {
    private final IntegerProperty userID;
    private final StringProperty userName;
    private final StringProperty password;
    private final BooleanProperty active;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdateBy;
    
    private Boolean loggedin = false;
    
    
    public User() {
        this.userID = new SimpleIntegerProperty();
        this.userName  = new SimpleStringProperty();
        this.password  = new SimpleStringProperty();
        this.active = new SimpleBooleanProperty();
        this.createDate  = new SimpleStringProperty();
        this.createdBy  = new SimpleStringProperty();
        this.lastUpdate  = new SimpleStringProperty();
        this.lastUpdateBy  = new SimpleStringProperty();
 
    }
    
    public void setLoggedInStatus(Boolean l) {
        this.loggedin = l;
    }
    
    public Boolean getLoggedInStatus () {
        return this.loggedin;
    }
    
    public IntegerProperty getUserIDProperty() {
        return this.userID;
    }
    
    public StringProperty getUserNameProperty() {
        return this.userName;
    }
     public StringProperty getPasswordProperty() {
        return this.password;
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
    public StringProperty getLastUpdateByProperty() {
        return this.lastUpdateBy;
    }
    
    public  void setUserID(int id) {
        this.userID.set(id);
    } 
    public  int getUserID() {
        return userID.get();
    }
    public  void setUserName(String name) {
        this.userName.set(name);
    } 
    public  String getUserName() {
        return userName.get();
    }
    public  void setPassword(String pass) {
        this.password.set(pass);
    } 
    public  String getPassword() {
        return password.get();
    }
    
    public  void setActive(Boolean id) {
        this.active.set(id);
    } 
    public  Boolean getActive() {
        return active.get();
    }
    public  void setCreateDate(String date) {
        this.createDate.set(date);
    } 
    public  String getCreateDate() {
        return createDate.get();
    }
    public  void setCreatedBy(String name) {
        this.createdBy.set(name);
    } 
    public  String getCreatedBy() {
        return createdBy.get();
    }
    public  void setLastUpdate(String date) {
        this.lastUpdate.set(date);
    } 
    public  String getLastUpdate() {
        return lastUpdate.get();
    }
    public  void setLastUpdateBy(String name) {
        this.lastUpdateBy.set(name);
    } 
    public  String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

}

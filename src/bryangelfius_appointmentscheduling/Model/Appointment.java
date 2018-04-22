/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.Model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bgelfius
 */
public class Appointment {
    private final IntegerProperty appointmentID;
    private final IntegerProperty customerID;
//    private final IntegerProperty userID;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty location;
    private final StringProperty contact;
    //private final StringProperty type;
    private final StringProperty url;
    private final StringProperty start;
    private final StringProperty end;
    private final StringProperty createDate;
    private final StringProperty createdBy;
    private final StringProperty lastUpdate;
    private final StringProperty lastUpdatedBy;
    
    private int rc;
    private ArrayList errorList;
    
    
    
    public Appointment () {
        this.appointmentID = new SimpleIntegerProperty();
        this.customerID  = new SimpleIntegerProperty();
//        this.userID  = new SimpleIntegerProperty();
        this.title  = new SimpleStringProperty();
        this.description  = new SimpleStringProperty();
        this.location  = new SimpleStringProperty();
        this.contact  = new SimpleStringProperty();
        //this.type  = new SimpleStringProperty();
        this.url  = new SimpleStringProperty();
        this.start  = new SimpleStringProperty();
        this.end  = new SimpleStringProperty();
        this.createDate  = new SimpleStringProperty();
        this.createdBy  = new SimpleStringProperty();
        this.lastUpdate  = new SimpleStringProperty();
        this.lastUpdatedBy  = new SimpleStringProperty();
 
    }
    
    
    public IntegerProperty getAppointmentIDProperty() {
        return this.appointmentID;
    }
    public IntegerProperty getCustomerIDProperty() {
        return this.customerID;
    }
//    public IntegerProperty getUserIDProperty() {
//        return this.userID;
//    }
    public StringProperty getTitleProperty() {
        return this.title;
    }
    public StringProperty getDescriptionProperty() {
        return this.description;
    }
    public StringProperty getLocationProperty() {
        return this.location;
    }
    public StringProperty getContactProperty() {
        return this.contact;
    }
//    public StringProperty getTypeProperty() {
//        return this.type;
//    }
    public StringProperty getUrlProperty() {
        return this.url;
    }
    public StringProperty getStartProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime rDate = LocalDateTime.parse(this.start.getValue(), df);
        
        // date stored as UTC
        ZonedDateTime zonedDate = rDate.atZone(ZoneId.of("UTC"));
        
        // now convert for local time
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zonedDate.withZoneSameInstant(zid); 

        StringProperty d = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
       
        return d;
    }
    public StringProperty getEndProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime rDate = LocalDateTime.parse(this.end.getValue(), df);
        
        // date stored as UTC
        ZonedDateTime zonedDate = rDate.atZone(ZoneId.of("UTC"));
        
        // now convert for local time
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zonedDate.withZoneSameInstant(zid); 

        StringProperty d = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
       
        return d;
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

    public  void setAppointmentID(int id) {
        this.appointmentID.set(id);
    } 
    public  int getAppointmentID() {
        return appointmentID.get();
    }
    public  void setCustomerID(int id) {
        this.customerID.set(id);
    } 
    public  int getCustomerID() {
        return customerID.get();
    }
    // field is on the ERD but not in the database
//    public  void setUserID(int id) {
//        this.userID.set(id);
//    } 
//    public  int getUserID() {
//        return userID.get();
//    }
    public  void setTitle(String id) {
        this.title.set(id);
    } 
    public  String getTitle() {
        return title.get();
    }
    public  void setDescription(String id) {
        this.description.set(id);
    } 
    public  String getDescription() {
        return description.get();
    }
    public  void setLocation(String id) {
        this.location.set(id);
    } 
    public  String getLocation() {
        return location.get();
    }
    public  void setContact(String id) {
        this.contact.set(id);
    } 
    public  String getContact() {
        return contact.get();
    }
//    public  void setType(String id) {
//        this.type.set(id);
//    } 
//    public  String getType() {
//        return type.get();
//    }
    public  void setUrl(String id) {
        this.url.set(id);
    } 
    public  String getUrl() {
        return url.get();
    }
    public  void setStart(String id) {
        this.start.set(id);
    } 
    public  String getStart() {
        return start.get();
    }
    
    public String createTimestamp(LocalDate sDate, int hour, int min, String ampm ) {
        
        if ( ampm.equals("PM") ) {
            hour = hour + 12;
        }
                  
        String lDate = String.format("%s %02d:%02d", sDate.toString(), hour, min);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm"); 
 
	LocalDateTime myDate = LocalDateTime.parse(lDate, df);
                
        // convert to time in UTC
        ZoneId zid = ZoneId.systemDefault();

        ZonedDateTime zonedDate = myDate.atZone(zid);
        
        ZonedDateTime utcDate = zonedDate.withZoneSameInstant(ZoneId.of("UTC"));
        
        myDate = utcDate.toLocalDateTime();
        
        Timestamp dateTS = Timestamp.valueOf(myDate); 
        
        
        return dateTS.toString();
    }
    
    
    public LocalDateTime getStartDateTime() {
              
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime rDate = LocalDateTime.parse(this.start.getValue(), df);
        
        // date stored as UTC
        ZonedDateTime zonedDate = rDate.atZone(ZoneId.of("UTC"));
        
        // now convert for local time
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zonedDate.withZoneSameInstant(zid); 

        return utcDate.toLocalDateTime();
        
    }
    
    public LocalDateTime getEndDateTime() {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime rDate = LocalDateTime.parse(this.end.getValue(), df);
        
        // date stored as UTC
        ZonedDateTime zonedDate = rDate.atZone(ZoneId.of("UTC"));
        
        // now convert for local time
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zonedDate.withZoneSameInstant(zid); 

        return utcDate.toLocalDateTime();
        
    }
    
    public  void setEnd(String id) {
        this.end.set(id);
    } 
    public  String getEnd() {
        return end.get();
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
    
    public int validateAppointment() {
                
        this.errorList = new ArrayList();
        this.rc = 0;
        
        if (this.getTitle().equals("")) {
            this.rc = -1;
            this.errorList.add("Title must be valued");
        }
                
        if (this.getDescription().equals("")) {
            this.rc = -1;
            this.errorList.add("Description must be valued");
        }
        
        if (this.getLocation().equals("")) {
            this.rc = -1;
            this.errorList.add("Location must be valued");
        }
                
        if (this.getContact().equals("")) {
            this.rc = -1;
            this.errorList.add("Contact must be valued");
        }
                  
        if (this.getUrl().equals("")) {
            this.rc = -1;
            this.errorList.add("Url must be valued");
        }
        
        LocalDateTime sDate = getStartDateTime();
        LocalDateTime eDate = getEndDateTime();
        
        
        // business hours 9-5
        if (sDate.getHour() < 9 || sDate.getHour() > 17) {
            this.rc = -1;
            this.errorList.add("Start time must be between 9am and 5pm");
        }
        
        if (eDate.getHour() < 9 || eDate.getHour() > 17) {
            this.rc = -1;
            this.errorList.add("End time must be between 9am and 5pm");
        }
          
        
        if (sDate.getHour() >= eDate.getHour() && sDate.getMinute() >= eDate.getMinute()) {
            this.rc = -1;
            this.errorList.add("End time must be later than start time");
        } 
        
        
        if (AppointmentDAO.appointmentOverlap(sDate.toString(), eDate.toString())) {
            this.rc = -1;
            this.errorList.add("Appointment overlaps with another appointment");
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

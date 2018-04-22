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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bgelfius
 */
public class AppointmentDAO {
    private static Appointment currentAppointment = new Appointment();
    
    
    public static Appointment getCurrentAppointment() {
        return currentAppointment;
    }
    
    public static void loadAppointment(int appointmentID) throws SQLException {
            try {
            String   query = "SELECT * "
                    + "FROM U04C2c.appointment  "
                    + " WHERE appointmentId = " + appointmentID;
            
            //System.out.println(query);
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
         
            
          
            //build current address
            while (rs.next()) {
               currentAppointment.setAppointmentID(rs.getInt("appointmentId"));
               currentAppointment.setCustomerID(rs.getInt("customerId"));
               //currentAppointment.setUserID(rs.getInt("addressId"));
               currentAppointment.setTitle(rs.getString("title"));
               currentAppointment.setDescription(rs.getString("description"));
               currentAppointment.setLocation(rs.getString("location"));
               currentAppointment.setContact(rs.getString("contact"));
               //currentAppointment.setType(rs.getString("type"));
               currentAppointment.setUrl(rs.getString("url"));
               currentAppointment.setStart(rs.getString("start"));
               currentAppointment.setEnd(rs.getString("end"));
               currentAppointment.setCreateDate(rs.getString("createDate"));
               currentAppointment.setCreatedBy(rs.getString("createdBy"));
               currentAppointment.setLastUpdate(rs.getString("lastUpdate"));
               currentAppointment.setLastUpdatedBy(rs.getString("lastUpdateBy"));
            }
    
            st.close();
            
                  
            
        } catch (SQLException ex) {
            throw ex;
        }
            
    }
    
    public static ObservableList<Appointment>  getAppointments(int customerID, String weekormonth) throws SQLException {
         ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

           try {
 
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                LocalDate sDate = LocalDate.now();
                LocalDate eDate = LocalDate.now();
                
                sDate = LocalDate.now();
                
                if (weekormonth.equals("Weekly")) {
                    eDate = LocalDate.now().plusDays(7);
                } else if(weekormonth.equals("Monthly")) {
                    eDate = LocalDate.now().plusMonths(1);
                } 
                
                
                String   query = "SELECT * "
                        + "FROM U04C2c.appointment  "
                        + " WHERE customerId = " + customerID
                        + " and start >= '" + sDate + "' and end <= '" + eDate + "'; ";

        
              //System.out.println(query);
                Statement st = DBHandler.getCurrentConnection().createStatement();
                ResultSet rs = st.executeQuery(query);
                Appointment a;
    


                //build current address
                while (rs.next()) {
                    a = new Appointment();
                    
                   a.setAppointmentID(rs.getInt("appointmentId"));
                   a.setCustomerID(rs.getInt("customerId"));
                   //currentAppointment.setUserID(rs.getInt("addressId"));
                   a.setTitle(rs.getString("title"));
                   a.setDescription(rs.getString("description"));
                   a.setLocation(rs.getString("location"));
                   a.setContact(rs.getString("contact"));
                   //a.setType(rs.getString("type"));
                   a.setUrl(rs.getString("url"));
                   a.setStart(rs.getString("start"));
                   a.setEnd(rs.getString("end"));
                   a.setCreateDate(rs.getString("createDate"));
                   a.setCreatedBy(rs.getString("createdBy"));
                   a.setLastUpdate(rs.getString("lastUpdate"));
                   a.setLastUpdatedBy(rs.getString("lastUpdateBy"));
                   
                   appointmentList.add(a);
                }

                st.close();

             
        } catch (SQLException ex) {
            throw ex;
        }
            
        return appointmentList;
        
    }
       
    public static void save(Appointment appt) throws SQLException {
            
        String query =  "INSERT INTO `U04C2c`.`appointment`\n" +
                        "(`customerId`,\n" +
                        "`title`,\n" +
                        "`description`,\n" +
                        "`location`,\n" +
                        "`contact`,\n" +
                        "`url`,\n" +
                        "`start`,\n" +
                        "`end`,\n" +
                        "`createDate`,\n" +
                        "`createdBy`,\n" +
                        "`lastUpdate`,\n" +
                        "`lastUpdateBy`)\n" +
                        "VALUES\n" +
                        "(" + appt.getCustomerID() + ",\n" +
                        " '" + appt.getTitle() + "',\n" +
                        " '" + appt.getDescription() + "',\n" +
                        " '" + appt.getLocation() + "',\n" +
                        " '" + appt.getContact() + "',\n" +
                        " '" + appt.getUrl() + "',\n" +
                        " '" + appt.getStart() + "',\n" +
                        " '" + appt.getEnd() + "',\n" +
                        "CURRENT_TIMESTAMP,\n" +
                        " '" + UserDAO.getCurrUser().getUserName() + "',\n" +
                        "CURRENT_TIMESTAMP,\n" +
                        " '" + UserDAO.getCurrUser().getUserName() + "');";
 
        //System.out.println(query);

        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        
        // inserted record now we need the key
        ResultSet generatedKeys = st.getGeneratedKeys();
        if (generatedKeys.next()) {
            loadAppointment(generatedKeys.getInt(1));
        }
        

    }
    
    public static void update(Appointment appt) throws SQLException {
        
        String query =  "UPDATE `U04C2c`.`appointment`\n" +
                        "SET\n" +
                        "`customerId` = " + appt.getCustomerID()+ ",\n" +
                        "`title` = '" + appt.getTitle()+ "',\n" +
                        "`description` = '" + appt.getDescription()+ "',\n" +
                        "`location` = '" + appt.getLocation()+ "',\n" +
                        "`contact` = '" + appt.getContact()+ "',\n" +
                        "`url` = '" + appt.getUrl()+ "',\n" +
                        "`start` = '" + appt.getStart()+ "',\n" +
                        "`end` = '" + appt.getEnd()+ "'," +
                        "`createDate` = '" + appt.getCreateDate()+ "',\n" +
                        "`createdBy` = '" + appt.getCreatedBy()+ "',\n" +
                        "`lastUpdate` = CURRENT_TIMESTAMP,\n" +
                        "`lastUpdateBy` = '" + UserDAO.getCurrUser().getUserName() + "'\n" +
                        "WHERE `appointmentId` = " + appt.getAppointmentID(); 

        //System.out.println(query);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query);
        
    }
    
    public static void delete(int appointmentID) throws SQLException {
        
        String query =  "DELETE FROM U04C2c.appointment  "
                    + " WHERE appointmentId = " + appointmentID + ";";
        

       // System.out.println(query);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        st.executeUpdate(query);
        
    }
    
    public static Boolean appointmentOverlap(String sDate, String eDate) {
           Boolean found = false;
        
        try {
            String query = "SELECT *\n" +
                           "FROM U04C2c.appointment a \n" +
                           "WHERE '" + sDate + "' BETWEEN a.start and a.end\n" +
                           "OR '" + eDate + "' BETWEEN a.start and a.end";
            
            Statement st = DBHandler.getCurrentConnection().createStatement();
            ResultSet rs = st.executeQuery(query); 

        // if this ID already exists, we quit
        if(rs.absolute(1)) {
            found = true;
        }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return found;
    }
    
    public static Boolean appointmentInNext15Minutes(LocalDateTime sDate) {
        Boolean found = false;
        
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
// 
	LocalDateTime myDate = sDate;
                
        // convert to time in UTC
        ZoneId zid = ZoneId.systemDefault();

        ZonedDateTime zonedDate = myDate.atZone(zid);
        
        ZonedDateTime utcDate = zonedDate.withZoneSameInstant(ZoneId.of("UTC"));
        
        myDate = utcDate.toLocalDateTime();
        
        LocalDateTime myDate2 = myDate.plusMinutes(15);
        Timestamp dateTS = Timestamp.valueOf(myDate); 
        Timestamp dateTS2 = Timestamp.valueOf(myDate2); 
        
        
     try {
         String query = "SELECT *\n" +
                        "FROM U04C2c.appointment a \n" +
                        "WHERE '" + myDate + "' between '" + myDate2 + "' and a.start\n" +
                        " AND '" + myDate.toLocalDate() +"' = DATE(a.start)" +
                       " AND lastUpdateBy = '"+ UserDAO.getCurrUser().getUserName() + "'";

         Statement st = DBHandler.getCurrentConnection().createStatement();
         ResultSet rs = st.executeQuery(query); 

     
     if(rs.absolute(1)) {
         found = true;
         loadAppointment(rs.getInt(1));
     }


     } catch (SQLException ex) {
         ex.printStackTrace();
     }


     return found;
 }
 
    public AppointmentDAO() {
        
    }
}

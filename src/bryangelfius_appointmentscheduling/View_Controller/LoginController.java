/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.View_Controller;

import bryangelfius_appointmentscheduling.Model.Appointment;
import bryangelfius_appointmentscheduling.Model.AppointmentDAO;
import bryangelfius_appointmentscheduling.Model.Customer;
import bryangelfius_appointmentscheduling.Model.CustomerDAO;
import bryangelfius_appointmentscheduling.Model.UserDAO;
import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class LoginController implements Initializable {

    
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Label usernameLabel;
    @FXML Label passwordLabel;
    @FXML Label titleLabel;
    @FXML Button loginButton;
    @FXML Button exitButton;
    
 
    // language aware error message
    private String invalidpassword;
    private String invalidheader;
    private String invalidtitle;
    
    // audit log path/filename
    private final String filename = "loginattempt.txt";
    
    public void handleExit(ActionEvent event) throws IOException, SQLException  {
        //Stage stage = (Stage) exitButton.getScene().getWindow();
        //stage.close();
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit System");
        alert.setHeaderText("Leave Appointment System");
        alert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DBHandler.close();
            Platform.exit();
        }
        
    }
    
    public void handleLogin(ActionEvent event) throws IOException, SQLException  {
        

        if (UserDAO.validateUser(username.getText(), password.getText())) {
            // valid login go to form
            writeAuditLog(username.getText());
            if (AppointmentDAO.appointmentInNext15Minutes(LocalDateTime.now()) ){
                Appointment appt = AppointmentDAO.getCurrentAppointment();
                CustomerDAO.loadCustomer(appt.getCustomerID());
                Customer cust = CustomerDAO.getCurrentCustomer();
                
                String sAppt = String.format("You have a meeting with %s at %02d:%02d", 
                                cust.getCustomerName(),appt.getStartDateTime().getHour(),
                                appt.getStartDateTime().getMinute());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setHeaderText("Appointment in next 15 minutes ");
                alert.setContentText(sAppt);
                alert.showAndWait();
            };
            
            Parent schedule_page = FXMLLoader.load( getClass().getResource("MainAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(invalidtitle);
            alert.setHeaderText(invalidheader);
            alert.setContentText(invalidpassword);
            alert.showAndWait();
        }
       
        
    }
    
    public void writeAuditLog(String username) {
        
        try {
             BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
 
            String line = String.format("[%s] -- Login detected by user [%s]\n", LocalDateTime.now().toString(), username);
            writer.append(line);
            
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        try {
            // Locale.setDefault(new Locale("fr", "FR"));
            
            rb = ResourceBundle.getBundle("bryangelfius_appointmentscheduling.LanguageFiles/loginform", Locale.getDefault());
            
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            String titlelabel = new String(rb.getString("appointment") + " " + rb.getString("scheduling"));
            titleLabel.setText(titlelabel);
            loginButton.setText(rb.getString("login"));
            exitButton.setText(rb.getString("exit"));
            invalidpassword = rb.getString("invalidpassword");
            invalidheader = rb.getString("invalidcredentials");
            invalidtitle = rb.getString("invalidcredentialtitle");
            
            DBHandler.open();
            

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
    }    
    
}

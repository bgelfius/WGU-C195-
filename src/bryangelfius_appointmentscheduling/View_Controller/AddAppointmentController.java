/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.View_Controller;

import bryangelfius_appointmentscheduling.Model.Appointment;
import bryangelfius_appointmentscheduling.Model.AppointmentDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static bryangelfius_appointmentscheduling.View_Controller.AppointmentViewController.selectedCustomerID;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class AddAppointmentController implements Initializable {
    private final ObservableList<Integer> hourList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
    private final ObservableList<Integer> minList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59);
    private final ObservableList<String> ampmList = FXCollections.observableArrayList("AM", "PM");
  
    private int selectedCustomer; 
    
    @FXML    private TextField title;
    @FXML    private TextField description;
    @FXML    private TextField location;
    @FXML    private TextField contact;
    @FXML    private TextField url;
    @FXML    private DatePicker apptDate;
    @FXML    private ComboBox startTimeHH;
    @FXML    private ComboBox startTimeMM;
    @FXML    private ComboBox startAMPM;
    @FXML    private ComboBox endTimeHH;
    @FXML    private ComboBox endTimeMM;
    @FXML    private ComboBox endAMPM;
    
    public void handleSave(ActionEvent event) throws IOException, SQLException  {
        Appointment currAppointment = new Appointment();
        
        currAppointment.setCustomerID(selectedCustomer);
        currAppointment.setTitle(title.getText());
        currAppointment.setDescription(description.getText());
        currAppointment.setLocation(location.getText());
        currAppointment.setContact(contact.getText());
        currAppointment.setUrl(url.getText());
       
        int shour = Integer.parseInt(startTimeHH.getValue().toString());
        int smin  =Integer.parseInt(startTimeMM.getValue().toString());
        int ehour = Integer.parseInt(endTimeHH.getValue().toString());
        int emin  =Integer.parseInt(endTimeMM.getValue().toString());
        
     
        String sDate = currAppointment.createTimestamp(apptDate.getValue(), shour, smin, startAMPM.getValue().toString());
        String eDate = currAppointment.createTimestamp(apptDate.getValue(), ehour, emin, endAMPM.getValue().toString());

        currAppointment.setStart(sDate);
        currAppointment.setEnd(eDate);
        

        if (currAppointment.validateAppointment() == 0) {
               AppointmentDAO.save(currAppointment);
               Parent schedule_page = FXMLLoader.load( getClass().getResource("AppointmentView.fxml"));   
               Scene schedule_page_scene = new Scene(schedule_page);
               Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               app_stage.setScene(schedule_page_scene);
               app_stage.show();
        } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Error: Adding Appointment");
               alert.setHeaderText("Missing Information");
               alert.setContentText(currAppointment.getErrors().get(0).toString());
               alert.showAndWait();
          }
        
        
        
        
        
    }
    
    public void handleExit(ActionEvent event) throws IOException  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Cancel Appointment");
        alert.setContentText("Are you sure you want to cancel and lose your updates?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent schedule_page = FXMLLoader.load( getClass().getResource("MainAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
        }
       }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // setup time combo boxes
         
        startTimeHH.setItems(hourList);
        startTimeMM.setItems(minList);
        startAMPM.setItems(ampmList);
        endTimeHH.setItems(hourList);
        endTimeMM.setItems(minList);
        endAMPM.setItems(ampmList);
        
        startTimeHH.setValue(8);
        startTimeMM.setValue(0);
        startAMPM.setValue("AM");
        endTimeHH.setValue(9);
        endTimeMM.setValue(0);
        endAMPM.setValue("AM");
        
        selectedCustomer = selectedCustomerID();
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.View_Controller;

import bryangelfius_appointmentscheduling.Model.Appointment;
import bryangelfius_appointmentscheduling.Model.AppointmentDAO;
import bryangelfius_appointmentscheduling.Model.Country;
import bryangelfius_appointmentscheduling.Model.Customer;
import bryangelfius_appointmentscheduling.Model.CustomerDAO;
import bryangelfius_appointmentscheduling.Model.UserDAO;
import static bryangelfius_appointmentscheduling.View_Controller.MainAppointmentController.selectedCustomer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class AppointmentViewController implements Initializable {
    @FXML  private Label customerName;
    @FXML  private TableView appointmentTable;
    @FXML  private TableColumn<Appointment, String> colTitle;
    @FXML  private TableColumn<Appointment, String> colDescription;
    @FXML  private TableColumn<Appointment, String> colLocation;
    @FXML  private TableColumn<Appointment, String> colContact;
    //@FXML  private TableColumn<Appointment, String> colType;
    @FXML  private TableColumn<Appointment, String> colStart;
    @FXML  private TableColumn<Appointment, String> colEnd;
   
    
    private ToggleGroup calendarType;
    @FXML    private  RadioButton weeklyRadioButton;
    @FXML    private  RadioButton monthlyRadioButton;
    
    
    private int selectCustomer;
    private static int selectCustomerID;
    private Customer currCust;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private int numDaysToDisplay;
  
    private static int selectedAppointment;
    
    public static int selectedAppointment(){
        return selectedAppointment;
    }
     public static int selectedCustomerID(){
        return selectCustomerID;
    }
     
  public void handleAdd(ActionEvent event) throws IOException  {
           
            Parent schedule_page = FXMLLoader.load( getClass().getResource("AddAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
       }
  
  public void handleDelete(ActionEvent event) throws IOException  {
        Appointment appt = new Appointment();
        appt = (Appointment) appointmentTable.getSelectionModel().getSelectedItems().get(0);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Are you sure you want to delete the appointment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        try {
            AppointmentDAO.delete(appt.getAppointmentID());
            
            Parent schedule_page = FXMLLoader.load( getClass().getResource("AppointmentView.fxml"));
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
            } catch (SQLException ex) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error: Delete");
                alert.setHeaderText("Can't Delete Appointment");
                alert.setContentText("There was an error trying to delete the appointment: " + ex.getMessage());
                alert.showAndWait();
            }
        }
        
       }
  public void handleChange(ActionEvent event) throws IOException  {
            Appointment appt = new Appointment();
            appt = (Appointment) appointmentTable.getSelectionModel().getSelectedItems().get(0);
            selectedAppointment = appt.getAppointmentID();
 
        
            Parent schedule_page = FXMLLoader.load( getClass().getResource("ModifyAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
       }
     public void handleExit(ActionEvent event) throws IOException  {
           
            Parent schedule_page = FXMLLoader.load( getClass().getResource("MainAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
       }
     
     public void loadAppointments() {
         
        try {
            RadioButton selectedRadioButton = (RadioButton) calendarType.getSelectedToggle();
            String selectedVal = selectedRadioButton.getText();
            
            
            
            appointmentList = AppointmentDAO.getAppointments(currCust.getCustomerID(), selectedVal.toString());
            
            colTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
            colDescription.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());     
            colLocation.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());     
            colContact.setCellValueFactory(cellData -> cellData.getValue().getContactProperty());     
            //colType.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());     
            colStart.setCellValueFactory(cellData -> cellData.getValue().getStartProperty());     
            colEnd.setCellValueFactory(cellData -> cellData.getValue().getEndProperty());     

            appointmentTable.setItems(appointmentList);  
             
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         
     }
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            this.calendarType = new ToggleGroup();
            this.weeklyRadioButton.setToggleGroup(this.calendarType);
            this.monthlyRadioButton.setToggleGroup(this.calendarType);
            
            selectCustomer = selectedCustomer();
            currCust = new Customer();
            CustomerDAO.loadCustomer(selectCustomer);
            currCust = CustomerDAO.getCurrentCustomer();
            customerName.setText(currCust.getCustomerName());
            selectCustomerID = currCust.getCustomerID();
            
            // lambda listener for toggle switch to handle changing the number of days to display.
            calendarType.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                loadAppointments();
            });
            
            this.calendarType.selectToggle(weeklyRadioButton);
 
            
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }    
    
}

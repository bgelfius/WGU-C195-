/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.View_Controller;

import bryangelfius_appointmentscheduling.Model.Customer;
import bryangelfius_appointmentscheduling.Model.CustomerDAO;
import bryangelfius_appointmentscheduling.Model.UserDAO;
import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bgelfius
 */
// interface for reports using lambdas

public class MainAppointmentController implements Initializable {

    @FXML  private TableView customerList;
    @FXML  private TableColumn<Customer, Integer> colCustomerID;
    @FXML  private TableColumn<Customer, String> colCustomerName;
    @FXML  private Button btnAppointmentsByMonth;
    @FXML  private Button btnScheduleByConsultant;
    @FXML  private Button btnCustomerAppointmentByMonth;
    
            
    private static int selectedCustomer;
    private static String whichReport;
    
    
    public static String selectedReport(){
        return whichReport;
    }
    public static int selectedCustomer(){
        return selectedCustomer;
    }
    
    public void handleReportButtons(ActionEvent event)  {
            
        try {
            Button x = (Button) event.getSource();
            
            whichReport = x.getText();
            
            
            Parent schedule_page = FXMLLoader.load( getClass().getResource("Reports.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
      

    
    public void handleAppointments(ActionEvent event) throws IOException  {
        
        
            Customer cust = new Customer();
            cust = (Customer) customerList.getSelectionModel().getSelectedItems().get(0);
            
            if (cust != null) {
                selectedCustomer = cust.getCustomerID();


                Parent schedule_page = FXMLLoader.load( getClass().getResource("AppointmentView.fxml"));   
                Scene schedule_page_scene = new Scene(schedule_page);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(schedule_page_scene);
                app_stage.show();
            } else {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Error: Nothing selected");
               alert.setHeaderText("Customer not selected");
               alert.setContentText("You must select a customer in order to view/update appointments");
               alert.showAndWait();
            }
     }
    
    public void handleExit(ActionEvent event) throws IOException  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to logout of the system?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            UserDAO.logoutCurrentUser();
            Parent schedule_page = FXMLLoader.load( getClass().getResource("Login.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
       }
    }
    
    public void handleDelete(ActionEvent event) throws IOException, SQLException  {
        Customer cust = new Customer();
        cust = (Customer) customerList.getSelectionModel().getSelectedItems().get(0);
        
         if (cust != null) {
            selectedCustomer = cust.getCustomerID();
            CustomerDAO.delete(selectedCustomer);

            Parent schedule_page = FXMLLoader.load( getClass().getResource("MainAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
         } else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Error: Nothing selected");
             alert.setHeaderText("Customer not selected");
             alert.setContentText("You must select a customer in order to delete");
             alert.showAndWait();
         }

    }
    
    public void handleModifyCustomer(ActionEvent event) throws IOException  {
        
        Customer cust = new Customer();
        cust = (Customer) customerList.getSelectionModel().getSelectedItems().get(0);
        
        if (cust != null) {
            selectedCustomer = cust.getCustomerID();

            //selectedCustomer = customerList.getSelectionModel().getSelectedItem();

            Parent schedule_page = FXMLLoader.load( getClass().getResource("ModifyCustomer.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
        } else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Error: Nothing selected");
             alert.setHeaderText("Customer not selected");
             alert.setContentText("You must select a customer in order to modify");
             alert.showAndWait();
        }
    }   
    
    public void handleAddCustomer(ActionEvent event) throws IOException  {
        
        Parent schedule_page = FXMLLoader.load( getClass().getResource("AddCustomer.fxml"));   
        Scene schedule_page_scene = new Scene(schedule_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(schedule_page_scene);
        app_stage.show();

    }   
    public void loadCustomers() {
        
        ObservableList<Customer> custList = CustomerDAO.getAllCustomers();

        colCustomerID.setCellValueFactory(cellData -> cellData.getValue().getCustomerIDProperty().asObject());
        colCustomerName.setCellValueFactory(cellData -> cellData.getValue().getCustomerNameProperty());     
        customerList.setItems(custList);   

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            // TODO
        loadCustomers();
        
        // lambdas for report buttons since all code was the same
        btnAppointmentsByMonth.setOnAction(event -> handleReportButtons(event));
        btnScheduleByConsultant.setOnAction(event -> handleReportButtons(event));
        btnCustomerAppointmentByMonth.setOnAction(event -> handleReportButtons(event));

    }    
    
}

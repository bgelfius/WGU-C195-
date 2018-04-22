/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.View_Controller;

import bryangelfius_appointmentscheduling.Model.Address;
import bryangelfius_appointmentscheduling.Model.City;
import bryangelfius_appointmentscheduling.Model.Country;
import bryangelfius_appointmentscheduling.Model.Customer;
import bryangelfius_appointmentscheduling.Model.CustomerDAO;
import bryangelfius_appointmentscheduling.Model.CityDAO;
import bryangelfius_appointmentscheduling.Model.AddressDAO;
import bryangelfius_appointmentscheduling.Model.CountryDAO;

import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import static bryangelfius_appointmentscheduling.View_Controller.MainAppointmentController.selectedCustomer;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class ModifyCustomerController implements Initializable {
   private int selectCustomer;
   
   private Customer currCust;
   private Address  currAddr;
   private City     currCity;
   private Country  currCountry;
   private ObservableList<Country> countryList = FXCollections.observableArrayList();
   private ObservableList<City> cityList = FXCollections.observableArrayList();

    @FXML    private TextField customerName;
    @FXML    private TextField address1;
    @FXML    private TextField address2;
    @FXML    private ComboBox  city;
    @FXML    private TextField postalCode;
    @FXML    private TextField phone;
    @FXML    private CheckBox customerActive;
    @FXML    private ComboBox  country;
  
 
       
    /**
     * Initializes the controller class.
     */
       public void handleSave(ActionEvent event) throws IOException, SQLException  {
           Boolean error = false;
            
           currAddr.setAddress(address1.getText());
           currAddr.setAddress2(address2.getText());
           currAddr.setPostalCode(postalCode.getText());
           currAddr.setPhone(phone.getText());
           currAddr.setCityID(CityDAO.getCityByName((String) city.getSelectionModel().getSelectedItem()));
 
           if (currAddr.validateAddress() == 0) {
                AddressDAO.update(currAddr);
            } else {
               error = true;
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Error: Updating Address");
               alert.setHeaderText("Missing Information");
               alert.setContentText(currAddr.getErrors().get(0).toString());
               alert.showAndWait();
            }
           
           
            if (!error) { 
                currCust.setCustomerName(customerName.getText());
                currCust.setActive(customerActive.isSelected());
 
                
                if (currCust.validateCustomer() == 0) {
                    CustomerDAO.update(currCust);
                    currCust = CustomerDAO.getCurrentCustomer();
                    Parent schedule_page = FXMLLoader.load( getClass().getResource("MainAppointment.fxml"));   
                    Scene schedule_page_scene = new Scene(schedule_page);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.setScene(schedule_page_scene);
                    app_stage.show();
                } else {
                    error = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error: Updating Customer");
                    alert.setHeaderText("Missing Information");
                    alert.setContentText(currCust.getErrors().get(0).toString());
                    alert.showAndWait();
                }
           }

       }
          
       public void handleCancel(ActionEvent event) throws IOException  {
           
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Cancel Modify Customer");
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
       
       
    
  
    public void loadCustomer(int customerID) throws SQLException {
        
        CustomerDAO.loadCustomer(customerID);
        currCust = CustomerDAO.getCurrentCustomer();
        
        AddressDAO.loadAddress(currCust.getAddressID());
        currAddr = AddressDAO.getCurrentAddress();
        
        CityDAO.loadCity(currAddr.getCityID());
        currCity = CityDAO.getCurrentCity();
        
        CountryDAO.loadCountry(currCity.getCountryID());
        currCountry = CountryDAO.getCurrentCountry();
            
    }
    
    public void loadScreen() throws SQLException {
        
        customerName.setText(currCust.getCustomerName());
        address1.setText(currAddr.getAddress());
        address2.setText(currAddr.getAddress2());
        postalCode.setText(currAddr.getPostalCode());
        phone.setText(currAddr.getPhone());
       
        cityList = CityDAO.getCities();
        
        for(City data : cityList)
        {
            city.getItems().add(data.getCity());
        }

     
        //select the current value
        city.setValue(currCity.getCity());
        customerActive.setSelected(currCust.getActive());
        
        countryList = CountryDAO.getCountries();
        
        for(Country data : countryList)
        {
            country.getItems().add(data.getCountry());
        }
        
        country.setValue(currCountry.getCountry());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
           // TODO
        selectCustomer = selectedCustomer();
        currCust = new Customer();
        currAddr = new Address();
        currCity = new City();
        currCountry = new Country();

        
        loadCustomer(selectCustomer);
        loadScreen();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
  
    }    
    
}

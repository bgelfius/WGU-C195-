/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_appointmentscheduling.View_Controller;

import bryangelfius_appointmentscheduling.Utilities.DBHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static bryangelfius_appointmentscheduling.View_Controller.MainAppointmentController.selectedReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

 
/**
 * FXML Controller class
 *
 * @author bgelfius
 */

public class ReportsController implements Initializable {
    @FXML  private Label reportTitle;
    @FXML  private TextArea reportData;
    
   
    private String whichReport;
    
    
       public void handleCancel(ActionEvent event) throws IOException  {
           
            Parent schedule_page = FXMLLoader.load( getClass().getResource("MainAppointment.fxml"));   
            Scene schedule_page_scene = new Scene(schedule_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(schedule_page_scene);
            app_stage.show();
      }
       
    public void populateReport1() throws SQLException {
        
        String query = "SELECT description, monthname(start) as 'Month', count(*) as 'Number'\n" +
                        " FROM U04C2c.appointment\n" +
                        " GROUP BY description, month(start)\n";
//            
        Statement st = DBHandler.getCurrentConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
 
        reportTitle.setText(whichReport);
        StringBuffer detailLine = new StringBuffer();      
        detailLine.append("Appointment Type                Month            Count\n");
        detailLine.append("======================================================\n");
    
        while (rs.next()) {
            
            detailLine.append(String.format("%s%s%d\n", String.format("%0$-32s", rs.getString("description")),
                    String.format("%0$-18s",rs.getString("Month")),rs.getInt("Number") ));
            
        }

        st.close();
       
        reportData.setText(detailLine.toString());
        
        
        
    }   
    public void populateReport2() throws SQLException {
        String query = "SELECT  a.lastUpdateBy, a.description, c.customerName,start, end\n" +
                        "FROM U04C2c.appointment a \n" +
                        "JOIN U04C2c.customer c on c.customerId = a.customerID\n" +
                        "GROUP BY a.lastUpdateBy, month(start),start\n";
 

        reportTitle.setText(whichReport);
        Statement st = DBHandler.getCurrentConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
 
        reportTitle.setText(whichReport);
        StringBuffer detailLine = new StringBuffer();      
        detailLine.append("Consultant                      Appointment            Customer           Start                      End\n");
        detailLine.append("=========================================================================================================================\n");
    
        while (rs.next()) {
            
            detailLine.append(String.format("%s%s%s%s     %s\n", String.format("%0$-32s", rs.getString("lastUpdateBy")),
                    String.format("%0$-24s",rs.getString("description")),
                    String.format("%0$-18s",rs.getString("customerName")),
                    rs.getString("start"),rs.getString("end")));
            
        }

        st.close();
       
        reportData.setText(detailLine.toString());
        
    }   
    public void populateReport3() throws SQLException {
        String query = "SELECT c.customerName, start, end\n" +
                   "FROM U04C2c.appointment a \n" +
                   "JOIN U04C2c.customer c on c.customerId = a.customerID\n" +
                   "GROUP BY c.customerid, month(start),start";
   
        reportTitle.setText(whichReport);
        
        Statement st = DBHandler.getCurrentConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
 
        reportTitle.setText(whichReport);
        StringBuffer detailLine = new StringBuffer();      
        detailLine.append("Customer Name                Start                      End\n");
        detailLine.append("============================================================================\n");
    
        while (rs.next()) {
            
            detailLine.append(String.format("%s%s     %s\n", String.format("%0$-29s", rs.getString("customerName")),
                    rs.getString("start"),rs.getString("end") ));
             
        }

        st.close();
       
        reportData.setText(detailLine.toString());
        
    }   
    
    
    public void loadReport() throws SQLException {
        
        if (whichReport.equals("Number of Appointments By Month")) {
            populateReport1();
        } else if (whichReport.equals("Schedule by Consultant")) {
            populateReport2();
        } else {
            populateReport3();
        }
        
    }   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.whichReport = selectedReport();
        
        try {
            loadReport();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }    
    
}

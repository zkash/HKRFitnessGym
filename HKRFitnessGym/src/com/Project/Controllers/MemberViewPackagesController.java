/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MemberViewPackagesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField searchPackage;
    //private  ObservableList<Package> data;
    private  ObservableList<Package> searchData;
    
    private int memberId = LoginStorage.getInstance().getId();
    
    @FXML private TableView<Package> memberViewPackagesTable;
    @FXML private TableColumn<Package, String> packageNameColumn;
    @FXML private TableColumn<Package, String> priceColumn; 
    @FXML private TableColumn<Package, String> startDateColumn;
    @FXML private TableColumn<Package, String> endDateColumn;
    @FXML private TableColumn<Package, String> startTimeColumn;
    @FXML private TableColumn<Package, String> endTimeColumn;
    
    @FXML private DatePicker subscriptionStartDatePicker;
    @FXML private DatePicker subscriptionEndDatePicker;
    
    ObservableList<Package> pack;
    
    private DBHandler dbHandler = new DBHandler();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            pack = dbHandler.memberViewPackages();
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            memberViewPackagesTable.setItems(null);
            memberViewPackagesTable.setItems(pack);
            
        } catch (SQLException ex) {
            Logger.getLogger(MemberViewPackagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void searchBtnClick(ActionEvent event) {
        
    }
    
    public void resetSearchBtnClick(ActionEvent event) {
        
    }
    
    public void deleteBtnClick(ActionEvent event) {
        
    }
    
    public void subscribeBtnClick(ActionEvent event) throws SQLException {
        ObservableList<Package> row , allRows;
        allRows = memberViewPackagesTable.getItems();
        row = memberViewPackagesTable.getSelectionModel().getSelectedItems(); 
        boolean subscriptionError = true;
        
        
        
        if (row.isEmpty()) {
            Helper.showDialogBox(subscriptionError, "Please select a package first to delete subscribe");
        }
        else {
            LocalDate subsriptionStartLocalDate = subscriptionStartDatePicker.getValue();
            LocalDate subsriptionEndLocalDate = subscriptionEndDatePicker.getValue();
            if (subsriptionStartLocalDate == null || subsriptionEndLocalDate  == null) {
                Helper.showDialogBox(subscriptionError, "Enter subscription start date and end date");
            }
            else {
                String packageName = row.get(0).getPackageName();
                Date packageStartDate = row.get(0).getStartDate();
                Date packageEndDate = row.get(0).getEndDate();
                int packageId = dbHandler.getPackageIdFromPackageName(packageName);
                System.out.println("pid" + packageId);
                Date subscriptionStartDate = Helper.convertLocalDateToSQLDate(subsriptionStartLocalDate);
                Date subscriptionEndDate = Helper.convertLocalDateToSQLDate(subsriptionEndLocalDate);
                if((subscriptionStartDate.before(packageStartDate) || subscriptionStartDate.after(packageEndDate))
                        || (subscriptionEndDate.before(packageStartDate) || subscriptionEndDate.after(packageEndDate))) {
                    Helper.showDialogBox(subscriptionError, "Subscription start date and end date must be within the range of Package start date and end date");
                }
                else {
                    Subscription subscription = new Subscription();
                    System.out.println("fskdljfdlskjkldsjf");
                    subscription.setSubscriptionStartDate((java.sql.Date)subscriptionStartDate);
                    subscription.setSubscriptionEndDate((java.sql.Date)subscriptionEndDate);
                    subscription.setPackageId(packageId);
                     System.out.println("wrreerw "  + subscription.getPackageId());
                    
                    
                    subscription.setPackageId(packageId);
                    subscription.setMemberId(memberId);
                    System.out.println("sssfsdfdsd "  + subscription.getSubscriptionStartDate());
                    subscriptionError = dbHandler.subscribeToPackage(subscription);
//                    subscriptionError = DBHandler.subscribeToPackage(new Subscription(
//                        (java.sql.Date) subscriptionStartDate,  //cast Java util date to SQL date
//                        (java.sql.Date)  subscriptionEndDate,
//                        packageId,
//                        memberId
//                    ));
                    if (subscriptionError) {
                        Helper.showDialogBox(subscriptionError, "Cannot make a subscription");
                    }
                    else {
                        Helper.showDialogBox(subscriptionError, "Successfully subscribe to a package");
                    }
                }
            } 
        }
    }
}

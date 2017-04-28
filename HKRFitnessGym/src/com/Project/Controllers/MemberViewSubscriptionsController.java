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
public class MemberViewSubscriptionsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField searchPackage;
    //private  ObservableList<Package> data;
    private  ObservableList<Package> searchData;
    
    //TODO Change later
    int memberId = 3;
    
    @FXML private TableView<Subscription> memberViewSubscriptionsTable;
    @FXML private TableColumn<Subscription, String> packageNameColumn;
    @FXML private TableColumn<Subscription, String> priceColumn; 
    @FXML private TableColumn<Subscription, String> packageStartDateColumn;
    @FXML private TableColumn<Subscription, String> packageEndDateColumn;
    @FXML private TableColumn<Subscription, String> startTimeColumn;
    @FXML private TableColumn<Subscription, String> endTimeColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStartDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionEndDateColumn;
    
    @FXML private DatePicker subscriptionStartDatePicker;
    @FXML private DatePicker subscriptionEndDatePicker;
    
    ObservableList<Subscription> subscription;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            subscription = DBHandler.memberViewSubscription();
            System.out.println(subscription);
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            packageStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            packageEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
            subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
            memberViewSubscriptionsTable.setItems(null);
            memberViewSubscriptionsTable.setItems(subscription);
            
        } catch (SQLException ex) {
            Logger.getLogger(MemberViewPackagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void cancelBtnClick(ActionEvent event) {
        
    }
    
    public void renewBtnClick(ActionEvent event) throws SQLException {
        ObservableList<Package> row , allRows;
//        allRows = memberViewPackagesTable.getItems();
//        row = memberViewPackagesTable.getSelectionModel().getSelectedItems(); 
//        boolean subscriptionError = true;
//        
//        
//        
//        if (row.isEmpty()) {
//            Helper.DialogBox(subscriptionError, "Please select a package first to delete subscribe");
//        }
//        else {
//            LocalDate subsriptionStartLocalDate = subscriptionStartDatePicker.getValue();
//            LocalDate subsriptionEndLocalDate = subscriptionEndDatePicker.getValue();
//            if (subsriptionStartLocalDate == null || subsriptionEndLocalDate  == null) {
//                Helper.DialogBox(subscriptionError, "Enter subscription start date and end date");
//            }
//            else {
//                String packageName = row.get(0).getPackageName();
//                Date packageStartDate = row.get(0).getStartDate();
//                Date packageEndDate = row.get(0).getEndDate();
//                int packageId = DBHandler.getPackageIdFromPackageName(packageName);
//                System.out.println("pid" + packageId);
//                Date subscriptionStartDate = Helper.convertLocalDateToSQLDate(subsriptionStartLocalDate);
//                Date subscriptionEndDate = Helper.convertLocalDateToSQLDate(subsriptionEndLocalDate);
//                if((subscriptionStartDate.before(packageStartDate) || subscriptionStartDate.after(packageEndDate))
//                        || (subscriptionEndDate.before(packageStartDate) || subscriptionEndDate.after(packageEndDate))) {
//                    Helper.DialogBox(subscriptionError, "Subscription start date and end date must be within the range of Package start date and end date");
//                }
//                else {
//                    Subscription subscription = new Subscription();
//                    System.out.println("fskdljfdlskjkldsjf");
//                    subscription.setSubscriptionStartDate((java.sql.Date)subscriptionStartDate);
//                    subscription.setSubscriptionEndDate((java.sql.Date)subscriptionEndDate);
//                    subscription.setPackageId(packageId);
//                     System.out.println("wrreerw "  + subscription.getPackageId());
//                    
//                    
//                    subscription.setPackageId(packageId);
//                    subscription.setMemberId(memberId);
//                    System.out.println("sssfsdfdsd "  + subscription.getSubscriptionStartDate());
//                    subscriptionError = DBHandler.subscribeToPackage(subscription);
////                    subscriptionError = DBHandler.subscribeToPackage(new Subscription(
////                        (java.sql.Date) subscriptionStartDate,  //cast Java util date to SQL date
////                        (java.sql.Date)  subscriptionEndDate,
////                        packageId,
////                        memberId
////                    ));
//                    if (subscriptionError) {
//                        Helper.DialogBox(subscriptionError, "Cannot make a subscription");
//                    }
//                    else {
//                        Helper.DialogBox(subscriptionError, "Successfully subscribe to a package");
//                    }
//                }
//            } 
//        }
    }
}

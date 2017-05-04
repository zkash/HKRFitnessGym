/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    
    private int memberId = LoginStorage.getInstance().getId();
    
    @FXML private TableView<Subscription> memberViewSubscriptionsTable;
    @FXML private TableColumn<Subscription, String> packageNameColumn;
    @FXML private TableColumn<Subscription, String> priceColumn; 
    @FXML private TableColumn<Subscription, String> packageStartDateColumn;
    @FXML private TableColumn<Subscription, String> packageEndDateColumn;
    @FXML private TableColumn<Subscription, String> startTimeColumn;
    @FXML private TableColumn<Subscription, String> endTimeColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStartDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionEndDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStatusColumn;
    
    @FXML private DatePicker subscriptionStartDatePicker;
    @FXML private DatePicker subscriptionEndDatePicker;
    
    ObservableList<Subscription> subscription;
    
    private DBHandler dbHandler = new DBHandler();
    private Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            subscription = dbHandler.memberViewSubscription();
            System.out.println(subscription);
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            packageStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            packageEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
            subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
            subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
            memberViewSubscriptionsTable.setItems(null);
            memberViewSubscriptionsTable.setItems(subscription);    
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MemberViewPackagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void cancelBtnClick(ActionEvent event) throws IOException, SQLException {
        ObservableList<Subscription> row , allRows;
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 
        boolean cancelError = true;
        if(row.isEmpty()) {
            helper.showDialogBox(cancelError, "Select a subscription first to cancel");
        }
        else {
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            if(subscriptionStatus.equals("Cancelled")) {
                helper.showDialogBox(cancelError, "You have already cancelled this subscription or this subscription has already expired");
            }
            else {
                int subscriptionId = row.get(0).getSubscriptionId();
                Optional<ButtonType> result = DialogBoxChoice( "Confirm Cancellation", "Are you sure you want to cancel this subscription?", "/com/Project/FXML/MemberViewSubscriptions");
                if (result.get().getText().equals("Yes")) {
                    cancelError = dbHandler.cancelSubscription(subscriptionId);
                    if (cancelError) {
                        helper.showDialogBox(cancelError, "Cannot cancel subscription");
                    }
                    else {
                        helper.showDialogBox(cancelError, "Successfully cancelled subscription");
                        row.forEach(allRows::remove);
                    }
                }
            }    
        }
        
    }
    
    public void renewBtnClick(ActionEvent event) throws SQLException {
        System.out.println("Hello");
        ObservableList<Subscription> row , allRows;
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 
        
        
        boolean renewError = true;
        java.util.Date currentDate = helper.getCurrentDate();
        
        System.out.println("ROW " + row);

        if(row.isEmpty()) {
            helper.showDialogBox(renewError, "Select a subscription first to renew");
        }
        else {
            
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            if(subscriptionStatus.equals("Active")) {
                helper.showDialogBox(renewError, "Your subscription has not expired yet");
            }
            else {
                LocalDate subscriptionStartLocalDate = subscriptionStartDatePicker.getValue();
                LocalDate subscriptionEndLocalDate = subscriptionEndDatePicker.getValue();
                if (subscriptionStartLocalDate == null || subscriptionEndLocalDate  == null) {
                        helper.showDialogBox(renewError, "Enter subscription start date and end date");
                }
                else {
                    Date subscriptionStartDate = helper.convertLocalDateToSQLDate(subscriptionStartLocalDate);
                    Date subscriptionEndDate = helper.convertLocalDateToSQLDate(subscriptionEndLocalDate);

                    if(subscriptionStartDate.before(currentDate) || subscriptionEndDate.before(currentDate)) {
                        helper.showDialogBox(renewError, "Subscription start date and end date cannot be earlier or later than current date");
                    }
                    else {
                        Date packageStartDate = row.get(0).getStartDate();
                        Date packageEndDate = row.get(0).getEndDate();
                        if((subscriptionStartDate.before(packageStartDate) || subscriptionStartDate.after(packageEndDate))
                                || (subscriptionEndDate.before(packageStartDate) || subscriptionEndDate.after(packageEndDate))) {
                            helper.showDialogBox(renewError, "Subscription start date and end date must be within the range of Package start date and end date");
                        }
                        else {
                            if(subscriptionStartDate.after(subscriptionEndDate)) {
                                helper.showDialogBox(renewError, "Subscription start date must be earlier than subscription end date");
                            }
                             else {
                                Subscription subscription = new Subscription();
                                subscription.setSubscriptionStartDate((java.sql.Date)subscriptionStartDate);
                                subscription.setSubscriptionEndDate((java.sql.Date)subscriptionEndDate);
                                String packageName = row.get(0).getPackageName();
                                int packageId = dbHandler.getPackageIdFromPackageName(packageName);
                                subscription.setPackageId(packageId);
                                subscription.setMemberId(memberId);
                                renewError = dbHandler.subscribeToPackage(subscription);
                                if (renewError) {
                                    helper.showDialogBox(renewError, "Cannot renew subscription");
                                }
                                else {
                                    helper.showDialogBox(renewError, "Successfully renewed subscription");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static Optional<ButtonType> DialogBoxChoice(String header, String content, String nextScene) throws IOException {    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(content);
        alert.setHeaderText(header);
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
}

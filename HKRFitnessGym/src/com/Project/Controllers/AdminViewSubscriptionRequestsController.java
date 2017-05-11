/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.SubscriptionRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewSubscriptionRequestsController implements Initializable {

   
     ObservableList<SubscriptionRequest> subscriptionRequest;
     
      @FXML private TableView<SubscriptionRequest> adminViewSubscriptionRequestTable;
      @FXML private TableColumn<SubscriptionRequest, String> memberFullNameColumn;
      @FXML private TableColumn<SubscriptionRequest, String> memberUsernameColumn;
    @FXML private TableColumn<SubscriptionRequest, String> packageNameColumn;
    @FXML private TableColumn<SubscriptionRequest, String> priceColumn; 
    @FXML private TableColumn<SubscriptionRequest, String> packageStartDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> packageEndDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> startTimeColumn;
    @FXML private TableColumn<SubscriptionRequest, String> endTimeColumn;
    @FXML private TableColumn<SubscriptionRequest, String> subscriptionStartDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> subscriptionEndDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> subscriptionStatusColumn;
    
    @FXML private TextField searchSubscription;
    @FXML private CheckBox searchMemberFullName;
    @FXML private CheckBox searchMemberUsername;
    @FXML private CheckBox searchPackageName;

    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    private int subscriptionId;
    
    private int adminId = LoginStorage.getInstance().getId();
    private ObservableList<SubscriptionRequest>  row, allRows;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            subscriptionRequest = dbHandler.getSubscriptionRequest();
            System.out.println(subscriptionRequest);
            System.out.println("QQQ");
            memberFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
            memberUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriberUsername"));
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            packageStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            packageEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
            subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
//            subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
            adminViewSubscriptionRequestTable.setItems(null);
            adminViewSubscriptionRequestTable.setItems(subscriptionRequest);    
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewSubscriptionRequestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void acceptRequestBtnClick(ActionEvent event) throws SQLException { 
        allRows = adminViewSubscriptionRequestTable.getItems();
        row = adminViewSubscriptionRequestTable.getSelectionModel().getSelectedItems(); 
        if(row.isEmpty()) {
            helper.showDialogBox(true, "Select a subscription reqeust to accept");
        }
        else {
            subscriptionId = row.get(0).getSubscriptionId();
            
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Offer Price");
            tid.setHeaderText("Enter the price that you want to  \noffer the member for this subscription");
            Optional<String> offerPriceTextField = tid.showAndWait();
            if(offerPriceTextField.isPresent()) {
                String offerPriceString = offerPriceTextField.get();
                if(!offerPriceString.isEmpty()) {
                    String offerPriceRegex = "^[0-9]*|[0-9]*.[0-9]{1,2}$";
                    if(offerPriceString.matches(offerPriceRegex)) {
                        float packageCost = row.get(0).getPrice();
                        float offerPrice = Float.valueOf(offerPriceString);
                        if((offerPrice > packageCost) && (offerPrice < 0)) {
                            helper.showDialogBox(true, "Offer price cannot be more than package price or less than 0.\nIt should be less than or equal to the package price.");
                        }
                        else {
                            System.out.println("SIDDD " + subscriptionId);
                            dbHandler.acceptSubscriptionRequest(subscriptionId, offerPrice, adminId);
                            row.forEach(allRows::remove);
                            helper.showDialogBox(true, "Request accepted");
                        }
                    }
                    else {
                        helper.showDialogBox(true, "The value provided is not a a valid price");
                    }
                }
                else {
                    helper.showDialogBox(true, "Provide offer price to accept subscription reqeust");
                }
            }
            
                    
        }
        
    }
    
    public void declineRequestBtnClick(ActionEvent event) throws SQLException {
        allRows = adminViewSubscriptionRequestTable.getItems();
        row = adminViewSubscriptionRequestTable.getSelectionModel().getSelectedItems(); 
        if(row.isEmpty()) {
            helper.showDialogBox(true, "Select a subscription reqeust to decline");
        }
        else {
            subscriptionId = row.get(0).getSubscriptionId();
            
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Offer Price");
            tid.setHeaderText("Enter the reason why you  \nare declining this subscription request");
            Optional<String> declineMessageTextField = tid.showAndWait();
            if(declineMessageTextField.isPresent()) {
                String declineMessage = declineMessageTextField.get();
                if(!declineMessage.isEmpty()) {
                    System.out.println("SIDDD " + subscriptionId);
                    dbHandler.declineSubscriptionRequest(subscriptionId, declineMessage, adminId);
                    row.forEach(allRows::remove);
                     helper.showDialogBox(true, "Request declined");
                }
                else {
                    helper.showDialogBox(true, "Please provide decline message to decline subscription reqeust");
                }
            }
            
                    
        }
        
    }
    
//    public void acceptSendBtnClick(ActionEvent event) throws SQLException {
////        allRows = adminViewSubscriptionRequestTable.getItems();
////        row = adminViewSubscriptionRequestTable.getSelectionModel().getSelectedItems();
////        subscriptionId = row.get(0).getSubscriptionId();
////        String offerPriceText = offerPriceTextField.getText();
////        float offerPrice = Float.valueOf(offerPriceText);
////        System.out.println("SIDDD " + subscriptionId);
////        dbHandler.acceptSubscriptionRequest(subscriptionId, offerPrice, adminId);
////        row.forEach(allRows::remove);
////        helper.showDialogBox(true, "Request accepted");
//        
//        
//    }
//    
//    public void declineSendBtnClick(ActionEvent event) throws SQLException {
//        allRows = adminViewSubscriptionRequestTable.getItems();
//        row = adminViewSubscriptionRequestTable.getSelectionModel().getSelectedItems();
//        subscriptionId = row.get(0).getSubscriptionId();
//        String declineMessage = declineMessageTextArea.getText();
//        System.out.println("SID " + subscriptionId);
//        dbHandler.declineSubscriptionRequest(subscriptionId, declineMessage, adminId);
//        row.forEach(allRows::remove);
//        helper.showDialogBox(true, "Request declined");
//    }
    
    public void searchBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchSubscription.getText(); 
        ArrayList<CheckBox> checkboxes = new ArrayList<>();
        checkboxes.add(searchMemberFullName);
        checkboxes.add(searchMemberUsername);
        checkboxes.add(searchPackageName);

        String memberFirstName = null, memberMiddleName = null, memberLastName = null, memberUsername = null, packageName = null, adminFirstName = null, adminMiddleName = null, adminLastName = null;
        
        if(searchMemberFullName.isSelected()) {
            memberFirstName = searchQuery;
            memberMiddleName = searchQuery;
            memberLastName = searchQuery;
        }

        if(searchMemberUsername.isSelected()) {
            memberUsername = searchQuery;
        }
        
        if(searchPackageName.isSelected()) {
            packageName = searchQuery;
        }
        
      
        
        System.out.println("0");
        
        if (!searchMemberFullName.isSelected() 
                && !searchMemberUsername.isSelected() 
                && !searchPackageName.isSelected()) {
            memberFirstName = searchQuery;
            memberMiddleName = searchQuery;
            memberLastName = searchQuery;
            packageName = searchQuery;
           
        }
        System.out.println("1");
        ObservableList<SubscriptionRequest> searchData;
        System.out.println("HOOOOOLLALSA");
        searchData = dbHandler.searchInAdminViewSubscriptionRequests(memberFirstName, memberMiddleName, memberLastName, memberUsername, packageName);
        System.out.println("SEARCH DATA " + searchData);
        adminViewSubscriptionRequestTable.getColumns().clear();
        System.out.println("AFTER SEARCH");
        memberFullNameColumn = new TableColumn("Member Name");
        memberUsernameColumn = new TableColumn("Member Username");
        packageNameColumn = new TableColumn("Package");
        priceColumn = new TableColumn("Price");
        packageStartDateColumn = new TableColumn("Package Start Date");
        packageEndDateColumn = new TableColumn("Package End Date");
        startTimeColumn = new TableColumn("Start Time");
        endTimeColumn = new TableColumn("End Time");
        subscriptionStartDateColumn = new TableColumn("Subscription Start Date");
        subscriptionEndDateColumn = new TableColumn("Subscription End Date");
          
         adminViewSubscriptionRequestTable.getColumns().addAll(memberFullNameColumn, memberUsernameColumn, packageNameColumn, priceColumn, packageStartDateColumn, packageEndDateColumn, startTimeColumn, endTimeColumn, subscriptionStartDateColumn, subscriptionEndDateColumn);
         memberFullNameColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004)); 
         memberUsernameColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         packageNameColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         priceColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         packageStartDateColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         packageEndDateColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         startTimeColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         endTimeColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         subscriptionStartDateColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         subscriptionEndDateColumn.prefWidthProperty().bind(adminViewSubscriptionRequestTable.widthProperty().multiply(0.30837004));
         // 
//      Set cell value factory to TableView
        setDataInTable(searchData);
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        
    }
    
    public void setDataInTable(ObservableList<SubscriptionRequest> data) {
        // Set cell value factory to TableView
        memberFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
            memberUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriberUsername"));
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            packageStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            packageEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
            subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
//            subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
            adminViewSubscriptionRequestTable.setItems(null);
            adminViewSubscriptionRequestTable.setItems(data); 
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML private Pane acceptRequestPane;
    @FXML private Pane declineRequestPane;
    @FXML private TextField offerPriceTextField;
    @FXML private TextArea declineMessageTextArea;
     ObservableList<SubscriptionRequest> subscriptionRequest;
     
      @FXML private TableView<SubscriptionRequest> adminViewSubscriptionRequestTable;
      @FXML private TableColumn<SubscriptionRequest, String> fullNameColumn;
      @FXML private TableColumn<SubscriptionRequest, String> usernameColumn;
    @FXML private TableColumn<SubscriptionRequest, String> packageNameColumn;
    @FXML private TableColumn<SubscriptionRequest, String> priceColumn; 
    @FXML private TableColumn<SubscriptionRequest, String> packageStartDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> packageEndDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> startTimeColumn;
    @FXML private TableColumn<SubscriptionRequest, String> endTimeColumn;
    @FXML private TableColumn<SubscriptionRequest, String> subscriptionStartDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> subscriptionEndDateColumn;
    @FXML private TableColumn<SubscriptionRequest, String> subscriptionStatusColumn;
    
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
            acceptRequestPane.setVisible(false);
            declineRequestPane.setVisible(false);
            subscriptionRequest = dbHandler.getSubscriptionRequest();
            System.out.println(subscriptionRequest);
            System.out.println("QQQ");
            fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriberUsername"));
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
                        float offerPrice = Float.valueOf(offerPriceString);
                        System.out.println("SIDDD " + subscriptionId);
                        dbHandler.acceptSubscriptionRequest(subscriptionId, offerPrice, adminId);
                        row.forEach(allRows::remove);
                        helper.showDialogBox(true, "Request accepted");
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
        
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        
    }
}


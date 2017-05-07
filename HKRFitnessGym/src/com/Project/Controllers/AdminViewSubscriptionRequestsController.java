/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.sql.SQLException;
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
            fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriberFullName"));
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
    
    public void acceptRequestBtnClick(ActionEvent event) { 
        declineRequestPane.setVisible(false);
        ObservableList<SubscriptionRequest> row , allRows;
        allRows = adminViewSubscriptionRequestTable.getItems();
        row = adminViewSubscriptionRequestTable.getSelectionModel().getSelectedItems(); 
        boolean cancelError = true;
        if(row.isEmpty()) {
            helper.showDialogBox(cancelError, "Select a subscription reqeust to accept");
        }
        else {
            acceptRequestPane.setVisible(true);
            subscriptionId = row.get(0).getSubscriptionId();
        }
        
    }
    
    public void declineRequestBtnClick(ActionEvent event) {
        acceptRequestPane.setVisible(false);
        declineRequestPane.setVisible(true);
    }
    
    public void acceptSendBtnClick(ActionEvent event) throws SQLException {
        String offerPriceText = offerPriceTextField.getText();
        float offerPrice = Float.valueOf(offerPriceText);
        dbHandler.acceptSubscriptionRequest(subscriptionId, offerPrice);
        helper.showDialogBox(true, "Request accepted");
        
        
    }
    
    public void declineSendBtnClick(ActionEvent event) throws SQLException {
        String declineMessage = declineMessageTextArea.getText();
        dbHandler.declineSubscriptionRequest(subscriptionId, declineMessage);
        helper.showDialogBox(true, "Request declined");
    }
}

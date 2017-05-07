/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewDeclinedSubscriptionRequestsController implements Initializable {

    @FXML private TableView<Subscription> adminViewSubscriptionsTable;
      @FXML private TableColumn<Subscription, String> memberFullNameColumn;
      @FXML private TableColumn<Subscription, String> memberUsernameColumn;
    @FXML private TableColumn<Subscription, String> packageNameColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStartDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionEndDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStatusColumn;
    @FXML private TableColumn<Subscription, String> adminFullNameColumn;
    @FXML private TableColumn<Subscription, String> declineMessageColumn;
    
    @FXML private TextField searchSubscription;
    @FXML private CheckBox searchMemberFullName;
    @FXML private CheckBox searchMemberUsername;
    @FXML private CheckBox searchPackageName;
    @FXML private CheckBox searchAdminFullName;
    
    private DBHandler dbHandler = new DBHandler();
    ObservableList<Subscription> subscription;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            subscription = dbHandler.adminViewDeclinedSubscription();
            
            
            System.out.println("QQQ");
            memberFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
            memberUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("memberUsername"));
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
            subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
            subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
            declineMessageColumn.setCellValueFactory(new PropertyValueFactory<>("declineMessage"));
            
            adminFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionAdminFullName"));
            
            adminViewSubscriptionsTable.setItems(null);
            adminViewSubscriptionsTable.setItems(subscription);    
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewDeclinedSubscriptionRequestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
        
        
    
    public void searchBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchSubscription.getText(); 
        ArrayList<CheckBox> checkboxes = new ArrayList<>();
        checkboxes.add(searchMemberFullName);
        checkboxes.add(searchMemberUsername);
        checkboxes.add(searchPackageName);
        checkboxes.add(searchAdminFullName);
        
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
        
        if(searchAdminFullName.isSelected()) {
            adminFirstName = searchQuery;
            adminMiddleName = searchQuery;
            adminLastName = searchQuery;
        }
        
        System.out.println("0");
        
        if (!searchMemberFullName.isSelected() 
                && !searchMemberUsername.isSelected() 
                && !searchPackageName.isSelected()
                && !searchAdminFullName.isSelected()) {
            memberFirstName = searchQuery;
            memberMiddleName = searchQuery;
            memberLastName = searchQuery;
            packageName = searchQuery;
            adminFirstName = searchQuery;
            adminMiddleName = searchQuery;
            adminLastName = searchQuery;
        }
        System.out.println("1");
        ObservableList<Subscription> searchData;
        System.out.println("HOOOOOLLALSA");
        searchData = dbHandler.searchInAdminViewDeclinedSubscription(memberFirstName, memberMiddleName, memberLastName, memberUsername, packageName, adminFirstName, adminMiddleName, adminLastName);
        adminViewSubscriptionsTable.getColumns().clear();
        memberFullNameColumn = new TableColumn("Member Name");
        memberUsernameColumn = new TableColumn("Member Username");
        packageNameColumn = new TableColumn("Package");
        subscriptionStartDateColumn = new TableColumn("Subscription Start Date");
        subscriptionEndDateColumn = new TableColumn("Subscription End Date");
        declineMessageColumn = new TableColumn("Decline Message");
        adminFullNameColumn = new TableColumn("Admin Name");
          
         adminViewSubscriptionsTable.getColumns().addAll(memberFullNameColumn, memberUsernameColumn, packageNameColumn, subscriptionStartDateColumn, subscriptionEndDateColumn, declineMessageColumn, adminFullNameColumn);
         memberFullNameColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004)); 
         memberUsernameColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004));
         packageNameColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004));
         subscriptionStartDateColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004));
         subscriptionEndDateColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004));
         declineMessageColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004));
         adminFullNameColumn.prefWidthProperty().bind(adminViewSubscriptionsTable.widthProperty().multiply(0.30837004));
         // 
//      Set cell value factory to TableView
        setDataInTable(searchData);
    }
 
    public void setDataInTable(ObservableList<Subscription> data) {
        // Set cell value factory to TableView
        memberFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
        memberUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("memberUsername"));
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
        subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
        subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
        declineMessageColumn.setCellValueFactory(new PropertyValueFactory<>("declineMessage"));

        adminFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionAdminFullName"));

        adminViewSubscriptionsTable.setItems(null);
        adminViewSubscriptionsTable.setItems(data);   
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        subscription = dbHandler.adminViewDeclinedSubscription();
        setDataInTable(subscription);
    }
}

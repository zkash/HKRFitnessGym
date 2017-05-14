package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.Subscription;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
public class AdminViewSubscriptionsController implements Initializable {
    @FXML private TableView<Subscription> adminViewSubscriptionsTable;
    @FXML private TableColumn<Subscription, String> memberFullNameColumn;
    @FXML private TableColumn<Subscription, String> memberUsernameColumn;
    @FXML private TableColumn<Subscription, String> packageNameColumn;
    @FXML private TableColumn<Subscription, String> pricePaidColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStartDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionEndDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStatusColumn;
    @FXML private TableColumn<Subscription, String> adminFullNameColumn;
    
    @FXML private TextField searchSubscription;
    @FXML private CheckBox searchMemberFullName;
    @FXML private CheckBox searchMemberUsername;
    @FXML private CheckBox searchPackageName;
    @FXML private CheckBox searchAdminFullName;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    ObservableList<Subscription> data;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String filter = "All";
            data = dbHandler.adminViewSubscription(filter);
            setDataInTable(data);
        } catch (SQLException ex) {
            helper.showDialogBox(true, "Could not fetch data from subscriptions and show in table because of an error");
        }
    }    
    
    
    /**
     * Searches for data as per user's query and filters
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String searchQuery = searchSubscription.getText(); 
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
        
        data = dbHandler.searchInAdminViewSubscription(memberFirstName, memberMiddleName, memberLastName, memberUsername, packageName, adminFirstName, adminMiddleName, adminLastName);
        setDataInTable(data);
        helper.fitColumns(adminViewSubscriptionsTable); 
    }
    
    
    /**
     * Resets the table with initial data
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleResetSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "All";
        setDataInTableByFilter(filter);
        
    }
    
    
    /**
     * Handles radio box with label 'All' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterAllSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "All";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Handles radio box with label 'Active' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterActiveSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Active";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Handles radio box with label 'Expired' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterExpiredSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Expired";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Handles radio box with label 'Cancelled' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterCancelledSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Cancelled";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Sets data in table view
     * @param data 
     */
    public void setDataInTable(ObservableList<Subscription> data) {
        memberFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
        memberUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("memberUsername"));
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        pricePaidColumn.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));
        subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
        subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
        subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
        adminFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionAdminFullName"));
        adminViewSubscriptionsTable.setItems(data);   
    }
    
    
    /**
     * Gets the data from database, sets in in table, and fixes the width of the columns
     * @param filter
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void setDataInTableByFilter(String filter) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.adminViewSubscription(filter);
        setDataInTable(data);
        helper.fitColumns(adminViewSubscriptionsTable); 
    }
}
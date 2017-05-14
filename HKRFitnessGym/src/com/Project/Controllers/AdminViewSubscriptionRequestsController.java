package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.SubscriptionRequest;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewSubscriptionRequestsController implements Initializable {
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
    
    @FXML private TextField searchSubscription;
    @FXML private CheckBox searchMemberFullName;
    @FXML private CheckBox searchMemberUsername;
    @FXML private CheckBox searchPackageName;

    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private ObservableList<SubscriptionRequest> data, row, allRows;
    
    private int subscriptionId;
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = dbHandler.getSubscriptionRequest();
            setDataInTable(data);     
        } 
        catch (SQLException ex) {
            helper.showDialogBox(true, "Could not fetch data from subscription requests and show in table because of an error");
        }
    }    
    
    
    /**
     * Accepts a subscription request
     * @param event
     * @throws SQLException 
     */
    public void handleAcceptRequestBtnClick(ActionEvent event) throws SQLException { 
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
    
    
    /**
     * Declines a subscription request
     * @param event
     * @throws SQLException 
     */
    public void handleDeclineRequestBtnClick(ActionEvent event) throws SQLException {
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

    
    
    /**
     * Searches for data as per user's query and filters
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String searchQuery = searchSubscription.getText(); 
        String memberFirstName = null, memberMiddleName = null, memberLastName = null, memberUsername = null, packageName = null;
        
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
        
        if (!searchMemberFullName.isSelected() 
                && !searchMemberUsername.isSelected() 
                && !searchPackageName.isSelected()) {
            memberFirstName = searchQuery;
            memberMiddleName = searchQuery;
            memberLastName = searchQuery;
            packageName = searchQuery;
        }

        data = dbHandler.searchInAdminViewSubscriptionRequests(memberFirstName, memberMiddleName, memberLastName, memberUsername, packageName);
        setDataInTable(data);
        helper.fitColumns(adminViewSubscriptionRequestTable); 
    }
    
    
    /**
     * Resets the table with initial data
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleResetSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.getSubscriptionRequest();
        setDataInTable(data);
        helper.fitColumns(adminViewSubscriptionRequestTable); 
    }
    
    
    /**
     * Sets data in table view
     * @param data 
     */
    public void setDataInTable(ObservableList<SubscriptionRequest> data) {
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
        adminViewSubscriptionRequestTable.setItems(data); 
    }
}
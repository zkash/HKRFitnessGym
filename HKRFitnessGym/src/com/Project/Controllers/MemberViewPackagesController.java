package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.Package;
import com.Project.Models.Subscription;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
    @FXML private TableView<Package> memberViewPackagesTable;
    @FXML private TableColumn<Package, String> packageNameColumn;
    @FXML private TableColumn<Package, String> priceColumn; 
    @FXML private TableColumn<Package, String> startDateColumn;
    @FXML private TableColumn<Package, String> endDateColumn;
    @FXML private TableColumn<Package, String> startTimeColumn;
    @FXML private TableColumn<Package, String> endTimeColumn;
    
    @FXML private DatePicker subscriptionStartDatePicker;
    @FXML private DatePicker subscriptionEndDatePicker;
    
    @FXML private TextField searchPackage;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private final int memberId = LoginStorage.getInstance().getId();
    
    ObservableList<Package> data;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = dbHandler.memberViewPackages();
            setDataInTable(data);    
        } 
        catch (SQLException | IllegalArgumentException | InvocationTargetException ex) {
            helper.showDialogBox(true, "Could not fetch data from database and show in table because of an error");
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
        String searchQuery = searchPackage.getText();
        data = dbHandler.searchInMemberViewPackage(searchQuery);
        setDataInTable(data);
        helper.fitColumns(memberViewPackagesTable);
    }
    
    
    /**
     * Resets the table with initial data
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleResetSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.memberViewPackages();
        setDataInTable(data);
        helper.fitColumns(memberViewPackagesTable);
    }

    
    /**
     * Sends a subscription request to admin
     * @param event
     * @throws SQLException 
     */
    public void handleSendSubscriptionRequestBtnClick(ActionEvent event) throws SQLException {
        ObservableList<Package> row , allRows;
        allRows = memberViewPackagesTable.getItems();
        row = memberViewPackagesTable.getSelectionModel().getSelectedItems(); 
        
        java.util.Date currentDate = helper.getCurrentDate();

        if (row.isEmpty()) {
            helper.showDialogBox(true, "Please select a package to send a subscription request");
        }
        else {
            LocalDate subsriptionStartLocalDate = subscriptionStartDatePicker.getValue();
            LocalDate subsriptionEndLocalDate = subscriptionEndDatePicker.getValue();
            
            if (subsriptionStartLocalDate == null || subsriptionEndLocalDate  == null) {
                helper.showDialogBox(true, "Enter subscription start date and end date");
            }
            else {
                String packageName = row.get(0).getPackageName();
                Date packageStartDate = row.get(0).getStartDate();
                Date packageEndDate = row.get(0).getEndDate();
                
                Date subscriptionStartDate = helper.convertLocalDateToSQLDate(subsriptionStartLocalDate);
                Date subscriptionEndDate = helper.convertLocalDateToSQLDate(subsriptionEndLocalDate);
               
                int packageId = dbHandler.getPackageIdFromPackageName(packageName);
                
                if(subscriptionStartDate.before(currentDate) || subscriptionEndDate.before(currentDate)) {
                        helper.showDialogBox(true, "Subscription start date and end date cannot be earlier than current date");
                }
                else {
                    if(subscriptionStartDate.before(packageStartDate) || 
                            subscriptionStartDate.after(packageEndDate) || 
                            subscriptionEndDate.before(packageStartDate) ||
                            subscriptionEndDate.after(packageEndDate)) {
                    helper.showDialogBox(true, "Subscription start date and end date must be within the range of Package start date and end date");
                    }
                    else {
                        if(subscriptionStartDate.after(subscriptionEndDate)) {
                            helper.showDialogBox(true, "Subscription start date must be earlier than subscription end date");
                        }
                        else {
                            Subscription subscription = new Subscription();
                            
                            subscription.setSubscriptionStartDate((java.sql.Date)subscriptionStartDate);
                            subscription.setSubscriptionEndDate((java.sql.Date)subscriptionEndDate);
                            subscription.setPackageId(packageId);
                            subscription.setMemberId(memberId);
                            
                            try {
                                dbHandler.subscribeToPackage(subscription);
                                helper.showDialogBox(false, "Successfully made a request to subscribe to a package");
                            }
                            catch(SQLException ex) {
                                helper.showDialogBox(true, "Cannot make a subscription");
                            }
                        }
                    }
                }
            } 
        }
    }
    
    
    /**
     * Sets data in table view
     * @param data
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void setDataInTable(ObservableList<Package> data) throws IllegalArgumentException, InvocationTargetException {
        // Set cell value factory to TableView
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        memberViewPackagesTable.setItems(data); 
    }
}
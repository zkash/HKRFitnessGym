package com.Project.Controllers;

import com.Project.Models.Admin;
import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewAdminAccountsController implements Initializable {
    @FXML private TableView<Admin> adminViewAccountsTable;
    @FXML private TableColumn<Admin, String> fullNameColumn;
    @FXML private TableColumn<Admin, String> usernameColumn;
    @FXML private TableColumn<Admin, String> ssnColumn;
    @FXML private TableColumn<Admin, String> dobColumn;
    @FXML private TableColumn<Admin, String> addressColumn;
    @FXML private TableColumn<Admin, String> genderColumn;
    @FXML private TableColumn<Admin, String> emailColumn;
    @FXML private TableColumn<Admin, String> phoneNumberColumn;

    @FXML private TextField searchAdmin;
    
    @FXML private CheckBox searchFullName;
    @FXML private CheckBox searchUsername;
    @FXML private CheckBox searchEmail;
    @FXML private CheckBox searchSSN;
    @FXML private CheckBox searchPhoneNumber;
    @FXML private CheckBox searchAddress;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private  ObservableList<Admin> data;
    
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = dbHandler.adminViewAdminAccounts();
            setDataInTable(data);
        } catch (SQLException ex) {
            helper.showDialogBox(true, "Data could not be fetched from database and set in table");
        }  
    }

    
    /**
     * Deletes an admin account
     * @param event ActionEvent
     * @throws SQLException
     * @throws IOException 
     */
    public void handleDeleteBtnClick(ActionEvent event) throws SQLException, IOException {
        ObservableList<Admin> row , allRows;
        allRows = adminViewAccountsTable.getItems();
        row = adminViewAccountsTable.getSelectionModel().getSelectedItems(); 
 
        if (row.isEmpty()) {
            helper.showDialogBox(true, "Please select an admin account first to delete the account");
        }
        else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            
            helper.showDialogBoxChoice(stage, "Confirm Deletion", "Are you sure you want to delete the admin account?", "com/Project/FXML/AdminViewAdminAccounts.fxml");
            
            try {
                String[] fullSSN = (row.get(0).getFullSSN()).split("-");
                int ssn1 = Integer.parseInt(fullSSN[0]);
                int ssn2 = Integer.parseInt(fullSSN[1]);
                String username = row.get(0).getUsername();
                
                dbHandler.deleteAccount(ssn1, ssn2, username, "Admin");
                helper.showDialogBox(false, "Admin successfully deleted");
                row.forEach(allRows::remove);
            }
            catch(NumberFormatException | SQLException ex) {
                helper.showDialogBox(true, "Could not delete admin because it is associated with other data in the system. \n\nDelete such data before trying to delete the admin");
            }
        }
    }
    
    
    /**
     * Searches for data as per user's query and filters
     * @param event ActionEvent
     * @throws SQLException 
     * @throws java.lang.reflect.InvocationTargetException 
     */
    public void handleSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String searchQuery = searchAdmin.getText(); 
        
        String fn = null, mn = null, ln = null, add = null, un = null, ead = null;
        int pnum = -1, ssn1 = -1, ssn2 = -1;
        
        if(searchFullName.isSelected()) {
            fn = searchQuery;
            mn = searchQuery;
            ln = searchQuery;
        }
        
        if(searchAddress.isSelected()) {
            add = searchQuery;
        }
        
        if(searchUsername.isSelected()) {
            un = searchQuery;
        }
        
        if(searchEmail.isSelected()) {
            ead = searchQuery;
        }
        
        if(searchPhoneNumber.isSelected()) {
            if(helper.hasChar(searchQuery)) {
                helper.showDialogBox(true, "Cannot search for text in phone number");
            }
            else {
                try {
                    pnum = Integer.parseInt(searchQuery);
                }
                catch (NumberFormatException e) {
                    helper.showDialogBox(true, "Cannot search for text in phone number");
                }
            }
        }
        
        if(searchSSN.isSelected()) {
            String ssnRegex = "([0-9]{6}-[0-9]{4})|([0-9]{10})";
            
            if (searchQuery.matches(ssnRegex)) {
                String[] ssnDivided = searchQuery.split("-");
                
                if(ssnDivided.length == 1) {
                    ssn1 = Integer.parseInt(searchQuery.substring(0, 6));
                    ssn2 = Integer.parseInt(searchQuery.substring(6, 10));
                }
                else if (ssnDivided.length == 2) {
                    ssn1 = Integer.parseInt(ssnDivided[0]);
                    ssn2 = Integer.parseInt(ssnDivided[1]);
                }
            }
            else {
                helper.showDialogBox(true, "Search query does not match SSN format (either 10 digits or 6 digits followed by - and 4 digits");
            }
        }
        
        if (!searchFullName.isSelected() 
                && !searchUsername.isSelected() 
                && !searchAddress.isSelected()
                && !searchSSN.isSelected()
                && !searchPhoneNumber.isSelected()
                && !searchEmail.isSelected()) {
            fn = searchQuery;
            mn = searchQuery;
            ln = searchQuery;
            un = searchQuery;
        }
        
        data= dbHandler.searchInAdminViewAdminAccounts(fn, mn, ln, add, un, ead, pnum, ssn1, ssn2, "Admin");
        setDataInTable(data);
        helper.fitColumns(adminViewAccountsTable);  
    }
    
    
    /**
     * Resets the table view with initial data
     * @param event ActionEvent
     * @throws SQLException 
     * @throws java.lang.reflect.InvocationTargetException 
     */
    public void handleResetSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.adminViewAdminAccounts();
        setDataInTable(data);
        helper.fitColumns(adminViewAccountsTable); 
    }
    
    
    /**
     * Sets data in table view
     * @param data OnservableList of Admin object
     * @throws IllegalArgumentException 
     */
    public void setDataInTable(ObservableList<Admin> data) {
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("fullSSN"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        adminViewAccountsTable.setItems(null);
        adminViewAccountsTable.setItems(data);
    }
}
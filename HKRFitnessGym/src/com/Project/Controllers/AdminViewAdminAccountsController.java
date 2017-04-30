/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
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
//import com.Project.Controllers.DBHandler;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewAdminAccountsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int adminSSN;
    private boolean login;
    
    @FXML private TableView<Admin> adminViewAccountsTable;
    @FXML private TableColumn<Admin, String> fullNameColumn;
    @FXML private TableColumn<Admin, String> usernameColumn;
    @FXML private TableColumn<Admin, String> ssnColumn;
    @FXML private TableColumn<Admin, String> dobColumn;
    @FXML private TableColumn<Admin, String> addressColumn;
    @FXML private TableColumn<Admin, String> genderColumn;
    @FXML private TableColumn<Admin, String> emailColumn;
    @FXML private TableColumn<Admin, String> phoneNumberColumn;
    
    private  ObservableList<Admin> data;
    private  ObservableList<Admin> searchData;
    @FXML private TextField searchAdmin;
    
    @FXML private CheckBox searchFullName;
    @FXML private CheckBox searchUsername;
    @FXML private CheckBox searchEmail;
    @FXML private CheckBox searchSSN;
    @FXML private CheckBox searchPhoneNumber;
    @FXML private CheckBox searchAddress;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adminSSN = LoginStatus.getSSN();
            login = LoginStatus.getLogin();
            data = DBHandler.adminViewAdminAccounts();
            setDataInTable(data);
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewAdminAccountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        data = DBHandler.adminViewAdminAccounts();
        setDataInTable(data);
    }
    
    
    public void deleteBtnClick(ActionEvent event) throws SQLException, IOException {
        System.out.println("gooo");
        ObservableList<Admin> row , allRows;
        allRows = adminViewAccountsTable.getItems();
        row = adminViewAccountsTable.getSelectionModel().getSelectedItems(); 
        
        boolean deletionError = true;
        
        if (row.isEmpty()) {
            Helper.showDialogBox(deletionError, "Please select an admin account first to delete the account");
        }
        else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Helper.showDialogBoxChoice(stage, "Confirm Deletion", "Are you sure you want to delete the admin account?", "com/Project/FXML/AdminViewAdminAccounts.fxml");
            try {
                String[] fullSSN = (row.get(0).getFullSSN()).split("-");
                int ssn1 = Integer.parseInt(fullSSN[0]);
                int ssn2 = Integer.parseInt(fullSSN[1]);
                String username = row.get(0).getUsername();
                deletionError = DBHandler.deleteAccount(ssn1, ssn2, username, "Admin");
           
            System.out.println("de " + deletionError);//
            }
            
            catch(Exception e) {
                deletionError = true;
            }
             System.out.println(deletionError);
            if (!deletionError) {
                Helper.showDialogBox(deletionError, "Admin successfully deleted");
                row.forEach(allRows::remove);
            }
            else {
                Helper.showDialogBox(deletionError, "Could not delete admin because it is associated with other data in the system. \n\nDelete such data before trying to delete the admin");
            }
        }
    }
    
    public void searchAdminBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchAdmin.getText(); 
        ArrayList<CheckBox> checkboxes = new ArrayList<>();
        checkboxes.add(searchFullName);
        checkboxes.add(searchUsername);
        checkboxes.add(searchEmail);
        checkboxes.add(searchSSN);
        checkboxes.add(searchPhoneNumber);
        checkboxes.add(searchAddress);
        
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
            if(Helper.hasChar(searchQuery)) {
                Helper.showDialogBox(true, "Cannot search for text in phone number");
            }
            else {
                try {
                    System.out.println("sfjkhdfs");
                    pnum = Integer.parseInt(searchQuery);
                }
                catch (Exception e) {
                    Helper.showDialogBox(true, "Cannot search for text in phone number");
                }
            }
        }
        
        if(searchSSN.isSelected()) {
            String ssnRegex = "([0-9]{6}-[0-9]{4})|([0-9]{10})";
            if (searchQuery.matches(ssnRegex)) {
                //try {
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
                Helper.showDialogBox(true, "Search query does not match SSN format (either 10 digits or 6 digits followed by - and 4 digits");
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
        
        searchData = DBHandler.searchInAdminViewAdminAccounts(fn, mn, ln, add, un, ead, pnum, ssn1, ssn2, "Admin");
        adminViewAccountsTable.getColumns().clear();
        fullNameColumn = new TableColumn("Full Name");
        usernameColumn = new TableColumn("Username");
        ssnColumn = new TableColumn("SSN");
        dobColumn = new TableColumn("DOB");
        genderColumn = new TableColumn("Gender");
        addressColumn = new TableColumn("Address");
        emailColumn = new TableColumn("Email");
        phoneNumberColumn = new TableColumn("Phone");
          
         adminViewAccountsTable.getColumns().addAll(fullNameColumn, usernameColumn, ssnColumn, genderColumn, dobColumn, addressColumn, emailColumn, phoneNumberColumn);
         fullNameColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.30837004)); 
         usernameColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
         ssnColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
         dobColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
         genderColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.10572688));
         addressColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.40));
         emailColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.40));
         phoneNumberColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
 
//      Set cell value factory to TableView
        setDataInTable(searchData);
        
    }
    
    public void setDataInTable(ObservableList<Admin> data) {
        // Set cell value factory to TableView
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
            System.out.println(data);
            //System.out.println(data.get(0).getDOB());
            // Set cell value factory to TableView
            fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            //ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn1 + ssn2"));
           // ssnColumn.setCellValueFactory(data -> Bindings.concat(data.getValue().ssn1Property(), "-", data.getValue().ssn2Property()));
            
             ssnColumn.setCellValueFactory(new PropertyValueFactory<>("fullSSN"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            adminViewAccountsTable.setItems(null);
            adminViewAccountsTable.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewAdminAccountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        data = DBHandler.adminViewAdminAccounts();
            // Set cell value factory to TableView
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("fullSSN"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        adminViewAccountsTable.setItems(null);
        adminViewAccountsTable.setItems(data);
    }
    
    
    public void deleteBtnClick(ActionEvent event) throws SQLException {
        ObservableList<Admin> row , allRows;
        allRows = adminViewAccountsTable.getItems();
        row = adminViewAccountsTable.getSelectionModel().getSelectedItems(); 
        boolean deletionError = false;
        try {
            deletionError = DBHandler.deleteAccount(row.get(0).getSSN1(), row.get(0).getSSN2(),"Admin");
        }
        catch(Exception e) {
            deletionError = true;
        }
        
        if (!deletionError) {
            Helper.DialogBox(deletionError, "Admin successfully deleted");
        }
        else {
            Helper.DialogBox(deletionError, "Could not delete admin because it is associated with other data in the system. \n\nDelete such data before trying to delete the admin");
        }
        System.out.println(row.get(0).getFullName()); 
        row.forEach(allRows::remove);
    }
    
    public void searchAdminBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchAdmin.getText(); 
        String sqlQuery = "";
        ArrayList<CheckBox> checkboxes = new ArrayList<>();
        checkboxes.add(searchFullName);
        checkboxes.add(searchUsername);
        checkboxes.add(searchEmail);
        checkboxes.add(searchSSN);
        checkboxes.add(searchPhoneNumber);
        checkboxes.add(searchAddress);
        
        Map<CheckBox, String> map = new HashMap<>();
        map.put(searchUsername, "username");
        map.put(searchEmail, "email");
        map.put(searchSSN, "ssn");
        map.put(searchPhoneNumber, "phoneNumber");
        map.put(searchAddress, "address");
        
        int checkboxCounter = 0;
        CheckBox cb = null;
        for(CheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                checkboxCounter++;
                cb = checkbox;
            }
        }
//        searchFieldStr = "";
//        
//        if (cb == searchFullName) {
//            searcchFieldStr = ""
//        }
        
        if(checkboxCounter == 1) {
            sqlQuery = cb + " LIKE '%" + searchQuery + "%' ";
        }
        else { 
            if(searchFullName.isSelected()) {
                sqlQuery = sqlQuery + " OR firstName LIKE '%" + searchQuery + "%' "
                        + "OR middleName LIKE '%" + searchQuery + "%' "
                        + "OR lastName LIKE '%" + searchQuery + "%' ";
            }
            if(searchUsername.isSelected()) {
                sqlQuery = sqlQuery + " OR username LIKE '%" + searchQuery + "%'";
            }

            if(searchEmail.isSelected()) {
                sqlQuery = sqlQuery + " OR email LIKE '%" + searchQuery + "%'";
            }

            if(searchSSN.isSelected()) {
                sqlQuery = sqlQuery + " OR ssn LIKE '%" + searchQuery + "%'";
            }

            if(searchPhoneNumber.isSelected()) {
                sqlQuery = sqlQuery + " OR phoneNumber LIKE '%" + searchQuery + "%'";
            }

            if(searchAddress.isSelected()) {
                sqlQuery = sqlQuery + " OR address LIKE '%" + searchQuery + "%'";
            }
        }
        
        System.out.println(sqlQuery);
        searchData = DBHandler.searchInAdminViewAdminAccounts(searchQuery, "Admin");

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
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("fullSSN"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        adminViewAccountsTable.setItems(null);
        adminViewAccountsTable.setItems(searchData);
    }
}

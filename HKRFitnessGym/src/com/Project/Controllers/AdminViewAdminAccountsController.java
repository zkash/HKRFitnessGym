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
    
    @FXML private TableView<Person> adminViewAccountsTable;
    @FXML private TableColumn<Person, String> fullNameColumn;
    @FXML private TableColumn<Person, String> usernameColumn;
    @FXML private TableColumn<Person, String> ssnColumn;
    @FXML private TableColumn<Person, String> dobColumn;
    @FXML private TableColumn<Person, String> addressColumn;
    @FXML private TableColumn<Person, String> genderColumn;
    @FXML private TableColumn<Person, String> emailColumn;
    @FXML private TableColumn<Person, String> phoneNumberColumn;
    
    private  ObservableList<Person> data;
    private  ObservableList<Person> searchData;
    @FXML private TextField searchAdmin;
    
    @FXML private CheckBox searchFullName;
    @FXML private CheckBox searchUsername;
    @FXML private CheckBox searchEmail;
    @FXML private CheckBox searchSSN;
    @FXML private CheckBox searchPhoneNumber;
    @FXML private CheckBox searchAddress;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminSSN = loginStatus.getSSN();
        login = loginStatus.getLogin();
        try {
            ArrayList<ArrayList<String>> finalArray;
            data = DBHandler.adminViewAccounts("Admin");
            // Set cell value factory to TableView
            fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            adminViewAccountsTable.setItems(null);
            adminViewAccountsTable.setItems(data);
        } 
        catch (SQLException ex) {
            Logger.getLogger(AdminViewAdminAccountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewDetailsBtnClick(ActionEvent event) {
        //String selected = adminViewAccountsTable.selectionModel.getSelectedItem();
    }
    
    
    public void deleteBtnClick(ActionEvent event) {
        
    }
    
    public void searchAdminBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchAdmin.getText(); 
        searchData = DBHandler.searchInAdminViewAccounts(searchQuery, "Admin");

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
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        adminViewAccountsTable.setItems(null);
        adminViewAccountsTable.setItems(searchData);
    }
    
    public ArrayList<String> filterSearch(ActionEvent event) {
        ArrayList<String> checks = new ArrayList<>();
        if(searchFullName.isSelected()) {
            checks.add("firstName");
            checks.add("middleName");
            checks.add("lastName");
        }
        if(searchUsername.isSelected()) {
            checks.add("username");
        }
        if(searchAddress.isSelected()) {
            checks.add("address");
        }
        if(searchPhoneNumber.isSelected()) {
            checks.add("phoneNumber");
        }
        if(searchEmail.isSelected()) {
            checks.add("email");
        }
        if(searchSSN.isSelected()) {
            checks.add("ssn");
        }
        return checks;
    }
}

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
public class AdminViewMemberAccountsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private int adminSSN;
    private boolean login;
    
    @FXML private TableView<Member> adminViewAccountsTable;
    @FXML private TableColumn<Member, String> fullNameColumn;
    @FXML private TableColumn<Member, String> usernameColumn;
    @FXML private TableColumn<Member, String> ssnColumn;
    @FXML private TableColumn<Member, String> dobColumn;
    @FXML private TableColumn<Member, String> addressColumn;
    @FXML private TableColumn<Member, String> genderColumn;
    @FXML private TableColumn<Member, String> emailColumn;
    @FXML private TableColumn<Member, String> phoneNumberColumn;
    
    private  ObservableList<Member> data;
    private  ObservableList<Member> searchData;
    @FXML private TextField searchAdmin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adminSSN = LoginStatus.getSSN();
            login = LoginStatus.getLogin();
            ArrayList<ArrayList<String>> finalArray;
            data = DBHandler.adminViewMemberAccounts();
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
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewMemberAccountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewDetailsBtnClick(ActionEvent event) {
        //String selected = adminViewAccountsTable.selectionModel.getSelectedItem();
       
    }
    
    public void deleteBtnClick(ActionEvent event) throws SQLException {
        ObservableList<Member> row , allRows;
        allRows = adminViewAccountsTable.getItems();
        row = adminViewAccountsTable.getSelectionModel().getSelectedItems(); 
        boolean deletionError = false;
        //try {
            deletionError = DBHandler.deleteAccount(row.get(0).getSSN1(), row.get(0).getSSN2(), "Member");
        //}
        //catch(Exception e) {
        //    deletionError = true;
        //}
        
        if (!deletionError) {
            Helper.DialogBox(deletionError, "Member successfully deleted");
        }
        else {
            Helper.DialogBox(deletionError, "Could not delete member because it is associated with other data in the system. \n\nDelete such data before trying to delete the member");
        }
        System.out.println(row.get(0).getFullName()); 
        row.forEach(allRows::remove);
    }
    
    public void searchMemberBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchAdmin.getText(); 
        searchData = DBHandler.searchInAdminViewMemberAccounts(searchQuery, "Member");
        System.out.println(searchData);
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
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        data = DBHandler.adminViewMemberAccounts();
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
}

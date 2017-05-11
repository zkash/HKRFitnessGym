/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.Member;
import java.io.IOException;
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
    @FXML private TableColumn<Member, String> adminFullNameColumn;
    
    private  ObservableList<Member> data;
    private  ObservableList<Member> searchData;
    @FXML private TextField searchMember;
    
    @FXML private CheckBox searchFullName;
    @FXML private CheckBox searchUsername;
    @FXML private CheckBox searchEmail;
    @FXML private CheckBox searchSSN;
    @FXML private CheckBox searchPhoneNumber;
    @FXML private CheckBox searchAddress;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            ArrayList<ArrayList<String>> finalArray;
            data = dbHandler.adminViewMemberAccounts();
            setDataInTable(data);
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewMemberAccountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewDetailsBtnClick(ActionEvent event) {
        //String selected = adminViewAccountsTable.selectionModel.getSelectedItem();
       
    }
    
    public void deleteBtnClick(ActionEvent event) throws SQLException, IOException {
        ObservableList<Member> row , allRows;
        allRows = adminViewAccountsTable.getItems();
        row = adminViewAccountsTable.getSelectionModel().getSelectedItems(); 
        boolean deletionError = true;
        if (row.isEmpty()) {
            helper.showDialogBox(deletionError, "Please select an member account first to delete the account");
        }
        else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            helper.showDialogBoxChoice(stage, "Confirm Deletion", "Are you sure you want to delete the member account?", "com/Project/FXML/AdminViewAdminAccounts.fxml");
            try {
                String[] fullSSN = (row.get(0).getFullSSN()).split("-");
                int ssn1 = Integer.parseInt(fullSSN[0]);
                int ssn2 = Integer.parseInt(fullSSN[1]);
                String username = row.get(0).getUsername();
                deletionError = dbHandler.deleteAccount(ssn1, ssn2, username, "Member");
            }
            catch(NumberFormatException | SQLException e) {
                deletionError = true;
            }

            if (!deletionError) {
                helper.showDialogBox(deletionError, "Member successfully deleted");
                row.forEach(allRows::remove);
            }
            else {
                helper.showDialogBox(deletionError, "Could not delete member because it is associated with other data in the system. \n\nDelete such data before trying to delete the member");
            }  
        }
    }
    
    public void searchMemberBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchMember.getText(); 
        
        
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
                //Helper.showDialogBox(true, "Cannot search for text in phone number");
            }
            else {
                try {
                    System.out.println("sfjkhdfs");
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
        
        searchData = dbHandler.searchInAdminViewMemberAccounts(fn, mn, ln, add, un, ead, pnum, ssn1, ssn2, "Member");
        
        adminViewAccountsTable.getColumns().clear();
        fullNameColumn = new TableColumn("Full Name");
        usernameColumn = new TableColumn("Username");
        ssnColumn = new TableColumn("SSN");
        dobColumn = new TableColumn("DOB");
        genderColumn = new TableColumn("Gender");
        addressColumn = new TableColumn("Address");
        emailColumn = new TableColumn("Email");
        phoneNumberColumn = new TableColumn("Phone");
        adminFullNameColumn = new TableColumn("Phone");
          
        adminViewAccountsTable.getColumns().addAll(fullNameColumn, usernameColumn, ssnColumn, genderColumn, dobColumn, addressColumn, emailColumn, phoneNumberColumn);
        fullNameColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.30837004)); 
        usernameColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
        ssnColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
        dobColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
        genderColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.10572688));
        addressColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.40));
        emailColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.40));
        phoneNumberColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
        adminFullNameColumn.prefWidthProperty().bind(adminViewAccountsTable.widthProperty().multiply(0.17621146));
        
        setDataInTable(searchData);
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        data = dbHandler.adminViewMemberAccounts();
        setDataInTable(data);
    }
    
    public void setDataInTable(ObservableList<Member> data) {
        // Set cell value factory to TableView
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("fullSSN"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        adminFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminFullName"));
        adminViewAccountsTable.setItems(null);
        adminViewAccountsTable.setItems(data);
        }
}
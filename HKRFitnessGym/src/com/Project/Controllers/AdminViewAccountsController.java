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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
//import com.Project.Controllers.DBHandler;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewAccountsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int adminSSN;
    private boolean login;
    
    @FXML private TableView adminViewAccountsTable;
    private final ObservableList<Object> data = FXCollections.observableArrayList();
    @FXML private TextField searchMember;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminSSN = loginStatus.getSSN();
        login = loginStatus.getLogin();
    }
    
    public void searchMemberBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchMember.getText();
        DBHandler.searchInAdminViewAccounts(searchQuery);
    }
}

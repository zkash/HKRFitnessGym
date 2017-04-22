/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewPackagesController implements Initializable {

    private int adminSSN;
    private boolean login;
    @FXML private TextField searchPackage;
    private  ObservableList<Package> data;
    private  ObservableList<Package> searchData;
    
    @FXML private TableView<Package> adminViewPackagesTable;
    @FXML private TableColumn<Package, String> packageNameColumn;
    @FXML private TableColumn<Package, String> startDateColumn;
    @FXML private TableColumn<Package, String> endDateColumn;
    @FXML private TableColumn<Package, String> startTimeColumn;
    @FXML private TableColumn<Package, String> endTimeColumn;
    @FXML private TableColumn<Package, String> membersColumn;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adminSSN = LoginStatus.getSSN();
            login = LoginStatus.getLogin();
            data = DBHandler.adminViewPackages();
            // Set cell value factory to TableView
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            //TODO Fix this
            membersColumn.setCellValueFactory(new PropertyValueFactory<>("0"));
            adminViewPackagesTable.setItems(null);
            adminViewPackagesTable.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewPackagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void searchPackageBtnClick(ActionEvent event) throws SQLException {
        String searchQuery = searchPackage.getText();
        DBHandler.searchInAdminViewPackage(searchQuery);
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        
    }
}

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
    @FXML private TableColumn<Package, String> priceColumn; 
    @FXML private TableColumn<Package, String> startDateColumn;
    @FXML private TableColumn<Package, String> endDateColumn;
    @FXML private TableColumn<Package, String> startTimeColumn;
    @FXML private TableColumn<Package, String> endTimeColumn;
    @FXML private TableColumn<Package, String> membersColumn;
    @FXML private TableColumn<Package, String> adminUsernameColumn;

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
        searchData = DBHandler.searchInAdminViewPackage(searchQuery);
        
        adminViewPackagesTable.getColumns().clear();
        packageNameColumn = new TableColumn("Package Name");
        priceColumn = new TableColumn("Price");
        startDateColumn = new TableColumn("Start Date");
        endDateColumn = new TableColumn("End Date");
        startTimeColumn = new TableColumn("Start Time");
        endTimeColumn = new TableColumn("End Time");
        membersColumn = new TableColumn("Members");
        adminUsernameColumn = new TableColumn("Admin");
          
         adminViewPackagesTable.getColumns().addAll(packageNameColumn, priceColumn, startDateColumn, endDateColumn, startTimeColumn, endTimeColumn, membersColumn, adminUsernameColumn);
         packageNameColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.30837004)); 
         priceColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.17621146));
         startDateColumn .prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.17621146));
         endDateColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.17621146));
         startTimeColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.10572688));
         endTimeColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.40));
         membersColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.40));
         adminUsernameColumn.prefWidthProperty().bind(adminViewPackagesTable.widthProperty().multiply(0.17621146));
 
//      Set cell value factory to TableView
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("0"));
        adminUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("Admin_ssn"));
        adminViewPackagesTable.setItems(null);
        adminViewPackagesTable.setItems(searchData);
    }
    
    public void resetSearchBtnClick(ActionEvent event) throws SQLException {
        
    }
}

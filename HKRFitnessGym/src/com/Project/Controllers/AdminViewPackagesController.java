/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    
    @FXML UpdatePackageInformationPageController updatePackageInformationPageController;

        ObservableList<Package> pack;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  updatePackageInformationPageController.injectAdminViewPackagesController(this);
        try {
            adminSSN = LoginStatus.getSSN();
            login = LoginStatus.getLogin();
            data = DBHandler.adminViewPackages();
            // Set cell value factory to TableView
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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
    
    public void searchBtnClick(ActionEvent event) throws SQLException {
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
        data = DBHandler.adminViewPackages();
        // Set cell value factory to TableView
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        //TODO Fix this
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("0"));
        adminViewPackagesTable.setItems(null);
        adminViewPackagesTable.setItems(data);
    }
    
    public void deleteBtnClick(ActionEvent event) throws SQLException {
        ObservableList<Package> row , allRows;
        allRows = adminViewPackagesTable.getItems();
        row = adminViewPackagesTable.getSelectionModel().getSelectedItems(); 
        boolean deletionError = false;
        //try {
            deletionError = DBHandler.deletePackage(row.get(0).getPackageName());
        //}
        //catch(Exception e) {
         //   deletionError = true;
        //}
        
        if (!deletionError) {
            Helper.DialogBox(deletionError, "Package successfully deleted");
        }
        else {
            Helper.DialogBox(deletionError, "Could not delete package because it is associated with other data in the system. \n\nDelete such data before trying to delete the package");
        }
        row.forEach(allRows::remove);
    }
    
    public void updateBtnClick(ActionEvent event) throws SQLException, IOException {
        ObservableList<Package> row = adminViewPackagesTable.getSelectionModel().getSelectedItems(); 
        String packageName = row.get(0).getPackageName();
        //ObservableList<Package> pack = DBHandler.getPackageInfoAdmin(packageName);
        pack = DBHandler.getPackageInfoAdmin(packageName);
        
        Node node = (Node) event.getSource();
       Stage stage = (Stage) node.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
        Parent root = (Parent)loader.load();
        updatePackageInformationPageController = loader.getController();
        updatePackageInformationPageController.setPackage(pack);
        //updatePackageInformationPageController = loader.<UpdatePackageInformationPageController>getController();
//       / updatePackageInformationPageController = (UpdatePackageInformationPageController)loader.getController();
       // updatePackageInformationPageController = new UpdatePackageInformationPageController();
        System.out.println(updatePackageInformationPageController);
      //  updatePackageInformationPageController.printer("Hello");
        Scene scene = new Scene(root);
       // Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        
//        System.out.println(pack);
//        
////        updatePackageInformationPageController = new UpdatePackageInformationPageController(pack);
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
////        //loader.setController(updatePackageInformationPageController);
////        Pane mainpane = (Pane) loader.load();
//        //updatePackageInformationPageController.setPackage(pack);
////        updatePackageInformationPageController = new UpdatePackageInformationPageController();
////        updatePackageInformationPageController.setPackage(pack);
//
//        FXMLLoader loader = new FXMLLoader();
//        Pane root = FXMLLoader.load(getClass().getResource("com/Project/FXML/UpdatePackageInformationPage.fxml"));
//        UpdatePackageInformationPageController updatePackageInformationPageController = (UpdatePackageInformationPageController)loader.getController();
//        updatePackageInformationPageController.printer("Hello");
////        FXMLLoader fxmlLoader = loader (getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
////        Parent root = (Parent) fxmlLoader.load();
//       // UpdatePackageInformationPageController updatePackageInformationPageController = fxmlLoader.<UpdatePackageInformationPageController>getController();
//        // updatePackageInformationPageController.setPackage(pack);
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
////        Node node = (Node)event.getSource();
////        Stage stage = (Stage)node.getScene().getWindow();
////        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
////        updatePackageInformationPageController = FXMLLoader.<UpdatePackageInformationPageController>getController();
////                
////                //new UpdatePackageInformationPageController();
////        updatePackageInformationPageController.setPackage(pack);
////        Scene scene = new Scene(root);
////        stage.setScene(scene);
////        
////        stage.show();
//        
////        ObservableList<Package> row = adminViewPackagesTable.getSelectionModel().getSelectedItems(); 
////        String packageName = row.get(0).getPackageName();
////        ObservableList<Package> pack = DBHandler.getPackageInfoAdmin(packageName);
//////        
//////        Stage stage = new Stage();
//////        FXMLLoader loader = new FXMLLoader();
//////        Pane root = loader.load(getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
//////        
////        Node node = (Node)event.getSource();
////        Stage stage = (Stage) node.getScene().getWindow();
////        
////        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
////        FXMLLoader loader = new FXMLLoader();
//////         upipc = (UpdatePackageInformationPageController)loader.getController();
//////        CreateUserPageController cupc = (CreateUserPageController)loader.getController();
//////        System.out.println(upipc);
//////        System.out.println(cupc);
////        //upipc.setFields(pack);
////        
////        Scene scene = new Scene(root);
////        stage.setScene(scene);
////        stage.show();
//        
//
//        
//        //UpdatePackageInformationPageController upipc = Loader.getController();
//        //upipc.setFields(pack);
//        //upipc.setAllFields(packageName, Float.NaN, sd, ed, packageName, packageName);
//        //((UpdatePackageInformationPageController)FXMLLoader.getController()).setPackageName(packageName);
////        stage.show();   
    }
    
//    public void printer() {
//        System.out.println("Printer");
//    }
   
}

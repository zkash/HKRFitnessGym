package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.Package;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewPackagesController implements Initializable {    
    @FXML private TableView<Package> adminViewPackagesTable;
    @FXML private TableColumn<Package, String> packageNameColumn;
    @FXML private TableColumn<Package, String> priceColumn; 
    @FXML private TableColumn<Package, String> startDateColumn;
    @FXML private TableColumn<Package, String> endDateColumn;
    @FXML private TableColumn<Package, String> startTimeColumn;
    @FXML private TableColumn<Package, String> endTimeColumn;
    @FXML private TableColumn<Package, String> membersColumn;
    @FXML private TableColumn<Package, String> adminFullNameColumn;
    
    @FXML private TextField searchPackage;
    
    @FXML private UpdatePackageInformationController updatePackageInformationPageController;

    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private  ObservableList<Package> data;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = dbHandler.adminViewPackages();
            setDataInTable(data);
        } 
        catch (SQLException | IllegalArgumentException | InvocationTargetException ex) {
            helper.showDialogBox(true, "Could not fetch data from database and show in table because of an error");
        }
    }    
    
    
    /**
     * Searches for data as per user's query and filters
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String searchQuery = searchPackage.getText();
        data = dbHandler.searchInAdminViewPackage(searchQuery);
        setDataInTable(data);
        helper.fitColumns(adminViewPackagesTable);  
    }
    
    
    /**
     * Resets the table with initial data
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleResetSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.adminViewPackages();
        setDataInTable(data);
        helper.fitColumns(adminViewPackagesTable); 
    }
    
    
    /**
     * Deletes a package from database and removes the row from the table view
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    public void handleDeleteBtnClick(ActionEvent event) throws SQLException, IOException {
        ObservableList<Package> row , allRows;
        allRows = adminViewPackagesTable.getItems();
        row = adminViewPackagesTable.getSelectionModel().getSelectedItems(); 
        
        if (row.isEmpty()) {
            helper.showDialogBox(true, "Please select a package first to delete it");
        }
        else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            
            helper.showDialogBoxChoice(stage, "Confirm Deletion", "Are you sure you want to delete the package?", "com/Project/FXML/AdminViewAdminAccounts.fxml");
            try {
                dbHandler.deletePackage(row.get(0).getPackageName());
                helper.showDialogBox(false, "Package successfully deleted");
                row.forEach(allRows::remove);
            }
            catch(SQLException e) {
                helper.showDialogBox(true, "Could not delete package because it is associated with other data in the system. \n\nDelete such data before trying to delete the package");
            }
        }
    }
    
    
    /**
     * Navigates to Update Package Information page
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    public void handleUpdateBtnClick(ActionEvent event) throws SQLException, IOException {
        ObservableList<Package> row = adminViewPackagesTable.getSelectionModel().getSelectedItems(); 
        String packageName = row.get(0).getPackageName();
        data = dbHandler.getPackageInfoAdmin(packageName);
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Project/Views/UpdatePackageInformation.fxml"));
        Parent root = (Parent)loader.load();
        
        updatePackageInformationPageController = loader.getController();
        updatePackageInformationPageController.setPackage(data);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Sets data in table view
     * @param data
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void setDataInTable(ObservableList<Package> data) throws IllegalArgumentException, InvocationTargetException {
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        adminFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminFullName"));
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSubscriber"));
        adminViewPackagesTable.setItems(data); 
    }
}
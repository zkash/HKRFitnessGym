/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.Announcement;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author KN
 */
public class AdminViewAnnouncementController implements Initializable {
    @FXML private TableView<com.Project.Models.Announcement> adminViewAnnouncementTable;
    @FXML private TableColumn<com.Project.Models.Announcement, Integer> adminColumn;
    @FXML private TableColumn<com.Project.Models.Announcement, String> dateColumn;
    @FXML private TableColumn<com.Project.Models.Announcement, String> timeColumn;
    @FXML private TableColumn<com.Project.Models.Announcement, String> titleColumn;
    @FXML private TableColumn<com.Project.Models.Announcement, String> bodyColumn;
    
    @FXML private TextField searchAnnouncement;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private  ObservableList<Announcement> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            data = dbHandler.adminViewAnnouncement();
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
        String searchQuery = searchAnnouncement.getText();
        data = dbHandler.searchInAdminViewAnnouncement(searchQuery);
        setDataInTable(data);
        helper.fitColumns(adminViewAnnouncementTable);  
    }
    
    
    /**
     * Resets the table with initial data
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void handleResetSearchBtnClick(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.adminViewAnnouncement();
        setDataInTable(data);
        helper.fitColumns(adminViewAnnouncementTable); 
    }
    
    
    /**
     * Deletes an announcement from database and removes the row from the table view
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    public void handleDeleteBtnClick(ActionEvent event) throws SQLException, IOException {
        ObservableList<com.Project.Models.Announcement> row , allRows;
        allRows = adminViewAnnouncementTable.getItems();
        row = adminViewAnnouncementTable.getSelectionModel().getSelectedItems(); 
        
        if (row.isEmpty()) {
            helper.showDialogBox(true, "Please select an announcement to delete.");
        }
        else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            
            helper.showDialogBoxChoice(stage, "Confirm Deletion", "Are you sure you want to delete the Announcemnt?", "com/Project/Views/AdminViewAnnouncemnt.fxml");
            try {
                dbHandler.deleteAnnouncement(row.get(0).getTitle(), row.get(0).getBody(), row.get(0).getTime(), row.get(0).getDate() );
                helper.showDialogBox(false, "Package successfully deleted");
                row.forEach(allRows::remove);
            }
            catch(SQLException e) {
                e.printStackTrace();
               // helper.showDialogBox(true, "Could not delete package because it is associated with other data in the system. \n\nDelete such data before trying to delete the package");
            }
        }
        
      //  public void updateAnnouncementBtnClick(ActionEvent event)throws SQLException, IOException {
            
        }
    
    /**
     * Sets data in table view
     * @param data
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */

    public void setDataInTable(ObservableList<Announcement> data) throws IllegalArgumentException, InvocationTargetException {
        // Set cell value factory to TableView
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bodyColumn.setCellValueFactory(new PropertyValueFactory<>("body"));
        adminViewAnnouncementTable.setItems(data); 
    }
    
}
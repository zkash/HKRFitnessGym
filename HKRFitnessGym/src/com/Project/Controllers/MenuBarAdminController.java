/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
import java.util.ArrayList;
public class MenuBarAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML MenuBar menuBarAdmin;
    private int adminSSN;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setSSN(int ssn) {
        this.adminSSN = ssn;
    }
    
    public void createAccountMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/CreateUserPage.fxml");
    }
    
    public void viewPersonalInformationMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/AdminViewPersonalInformation.fxml");
    }
    
    public void editPersonalInformationMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/UpdateAdminPersonalInformationPage.fxml");
    }
    
    public void viewAdminAccountsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/AdminViewAdminAccounts.fxml");
    }
    
    public void viewMemberAccountsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/AdminViewMemberAccounts.fxml");
    }
    
    public void createPackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/CreatePackagePage.fxml");
    }
    
    public void updatePackageMenuClick(ActionEvent event) {
        
    }
    
    public void deletePackageMenuClick(ActionEvent event) {
        
    }
    
    public void viewPackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/AdminViewPackages.fxml");
    }
    
    public void addScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/CreateSchedulePage.fxml");
    }
    
    public void updateScheduleMenuClick(ActionEvent event) {
        
    }
    
    public void deleteScheduleMenuClick(ActionEvent event) {
        
    }
    
    public void viewScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/AdminViewSchedule.fxml");
    }
    
    public void createAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/CreateAnnouncementPage.fxml");
    }
    
    public void updateAnnouncementMenuClick(ActionEvent event) {
        
    }
    
    public void deleteAnnouncementMenuClick(ActionEvent event) {
        
    }
    
    public void viewAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/AdminViewAnnouncements.fxml");
    }
    
    public void aboutMenuClick(ActionEvent event) {
        
    }
    
    public void setupAndShowStage(String fxmlFileURL) throws IOException {
        Stage stage = (Stage) menuBarAdmin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFileURL));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

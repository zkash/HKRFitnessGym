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
public class MenuBarMemberController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML MenuBar menuBarMember;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void editAccountMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("/com/Project/FXML/UpdateMemberPersonalInformationPage.fxml");
    }
    
    public void viewAccountMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("/com/Project/FXML/MemberViewPersonalInformation.fxml");
    }
    
    
    public void subscribePackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/MemberViewPackages.fxml");
    }
    
    public void viewSubscriptionMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/MemberViewSubscriptions.fxml");
    }
    
    
    public void viewScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/MemberViewSchedule.fxml");
    }
    
    public void viewAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("/com/Project/FXML/MemberViewAnnouncements.fxml");
    }
    
    public void visitChatRoomMenuClick(ActionEvent event) {
        
    }

    public void aboutMenuClick(ActionEvent event) {
        
    }
    
    public void setupAndShowStage(String fxmlFileURL) throws IOException {
        Stage stage = (Stage) menuBarMember.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFileURL));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

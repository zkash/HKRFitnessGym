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
    @FXML MenuBar menuBarMember;
    
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }    
    
    /**
     * Sets up the stage, parent, and scene and displays it
     * @param fxmlFile The name of the FXML file
     * @throws IOException 
     */
    public void setupAndShowStage(String fxmlFile) throws IOException {
        Stage stage = (Stage) menuBarMember.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/Views/" + fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Navigates to Update Personal Information page
     * @param event
     * @throws IOException 
     */
    public void handleUpdatePersonalInfoMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("UpdateMemberPersonalInformation.fxml");
    }
    
    
    /**
     * Navigates to View Personal Information page
     * @param event
     * @throws IOException 
     */
    public void handleViewPersonalInformationMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("MemberViewPersonalInformation.fxml");
    }
    
    
    /**
     * Navigates to View Packages page
     * @param event
     * @throws IOException 
     */
    public void handleSubscribeToPackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewPackages.fxml");
    }
    
    
    /**
     * Navigates to View Subscriptions page
     * @param event
     * @throws IOException 
     */
    public void handleViewSubscriptionsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewSubscriptions.fxml");
    }
    
    
    /**
     * Navigates to View Schedule page
     * @param event
     * @throws IOException 
     */
    public void handleViewScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewSchedule.fxml");
    }
    
    
    /**
     * Navigates to View Announcement page
     * @param event
     * @throws IOException 
     */
    public void handleViewAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewAnnouncement.fxml");
    }
    
    
    /**
     * Navigates to Chat page
     * @param event
     * @throws IOException 
     */
    public void handleVisitChatRoomMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberChat.fxml");
    }
    
    
    /**
     * Navigates to Change Password page
     * @param event
     * @throws IOException 
     */
    public void handleChangePasswordMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("MemberChangePassword.fxml");
    }  
}
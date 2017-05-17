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
public class MenuBarAdminController implements Initializable {
    @FXML MenuBar menuBarAdmin;
    
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
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
        Stage stage = (Stage) menuBarAdmin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/Views/" + fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Navigates to Create User page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleCreateAccountMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("CreateUser.fxml");
    }
    
    
    /**
     * Navigates to View Personal Information page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewPersonalInformationMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewPersonalInformation.fxml");
    }
    
    
    /**
     * Navigates to Update Personal Information page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleUpdatePersonalInformationMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("UpdateAdminPersonalInformation.fxml");
    }
    
    
    /**
     * Navigates to View Admin Accounts page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewAdminAccountsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewAdminAccounts.fxml");
    }
    
    
    /**
     * Navigates to View Member Accounts page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewMemberAccountsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewMemberAccounts.fxml");
    }
    
    
    /**
     * Navigates to Create Package page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleCreatePackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("CreatePackage.fxml");
    }

    
    /**
     * Navigates to View Package page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewPackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewPackages.fxml");
    }
    
    
    /**
     * Navigates to View Subscription Requests page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewSubscriptionRequestsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewSubscriptionRequests.fxml");
    }
    
    
    /**
     * Navigates to View Subscriptions page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewSubscriptionsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewSubscriptions.fxml");
    }
    
    
    /**
     * Navigates to View Declined Subscription Requests page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewDeclinedSubscriptionRequestsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewDeclinedSubscriptionRequests.fxml");
    }
    
    
    /**
     * Navigates to Create Schedule page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleAddScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("CreateSchedule.fxml");
    }
    
    
    /**
     * Navigates to View Schedule page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewSchedule.fxml");
    }
    
    
    /**
     * Navigates to Create Announcement page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleCreateAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("CreateAnnouncement.fxml");
    }
   
    
    /**
     * Navigates to View Announcement page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleViewAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminViewAnnouncement.fxml");
    }
    
    
    /**
     * Navigates to Chat page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleVisitChatRoomMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminChat.fxml");   
    }
    
    
    /**
     * Navigates to Change Password page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleChangePasswordMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("AdminChangePassword.fxml");
    }  
}

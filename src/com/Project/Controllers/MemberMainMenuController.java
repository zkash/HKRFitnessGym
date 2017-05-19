package com.Project.Controllers;

import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MemberMainMenuController implements Initializable {
    @FXML Label loggedUserLbl;
    Helper helper = new Helper();
    
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedUserLbl.textProperty().bind(LoginStorage.getInstance().usernameProperty());   
    }   
    
    
    /**
     * Navigates to Update Personal Information page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    private void handleUpdatePersonalInfoBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "UpdateMemberPersonalInformation.fxml");
    }  
    
    
    /**
     * Navigates to View Packages page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    private void handleSubscribeToPackageBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewPackages.fxml");
    }  
    
    
    /**
     * Navigates to View Subscriptions page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    private void handleViewSubscriptionsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewSubscriptions.fxml");
    } 
    
    
    /**
     * Navigates to View Schedule page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    private void handleViewScheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewSchedule.fxml");
    }  
    
    
    /**
     * Navigates to View Announcement page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    private void handleViewAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewAnnouncement.fxml"); 
    } 
    
    
    /**
     * Navigates to Change Password page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    private void handleSettingsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberChangePassword.fxml");
    }    
}

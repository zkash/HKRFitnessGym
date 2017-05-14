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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedUserLbl.textProperty().bind(LoginStorage.getInstance().usernameProperty());   
    }   
    
    
    /**
     * Navigates to Update Personal Information page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void updatePersonalInfoBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "UpdateMemberPersonalInformation.fxml");
    }  
    
    
    /**
     * Navigates to View Packages page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void subscribeToPackageBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewPackages.fxml");
    }  
    
    
    /**
     * Navigates to View Subscriptions page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void viewSubscriptionsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewSubscriptions.fxml");
    } 
    
    
    /**
     * Navigates to View Schedule page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void viewScheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewSchedule.fxml");
    }  
    
    
    /**
     * Navigates to View Announcement page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void viewAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewAnnouncement.fxml"); 
    } 
    
    
    /**
     * Navigates to Chat page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void chatBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberChat.fxml");
    }    
}
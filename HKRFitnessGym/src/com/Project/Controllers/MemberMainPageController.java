package com.Project.Controllers;

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
public class MemberMainPageController implements Initializable {
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
     * Navigate to Update Personal Information page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void updatePersonalInfoBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "UpdateMemberPersonalInformationPage.fxml");
    }  
    
    
    /**
     * Navigate to View Subscriptions page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void viewSubscriptionsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewSubscriptions.fxml");
    }  
    
    
    /**
     * Navigate to Chat page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void chatBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "ChatPage.fxml");
    }  
    

    /**
     * Navigate to View Schedule page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void viewScheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewSchedule.fxml");
    }  
    
    
    /**
     * Navigate to View Announcement page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void viewAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewAnnouncement.fxml");
    } 
    
    
    /**
     * Navigate to View Packages page
     * @param event
     * @throws IOException 
     */
    @FXML
    private void subscribeToPackageBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberViewPackages.fxml");
    } 
}
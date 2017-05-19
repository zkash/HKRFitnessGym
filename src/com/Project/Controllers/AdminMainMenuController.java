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
public class AdminMainMenuController implements Initializable {
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
     * Navigates to Create User page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    public void handleCreateAccountBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateUser.fxml");
    }
    
    
    /**
     * Navigates to Create Package page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    public void handleCreatePackageBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "CreatePackage.fxml");   
    }
    
    
    /**
     * Navigates to View Subscriptions page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    public void handleViewSubscriptionsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminViewSubscriptions.fxml");   
    } 
    
    
    /**
     * Navigates to Create Schedule page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    public void handleCreateScheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateSchedule.fxml");   
    }
    
    
    /**
     * Navigates to Create Announcement page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    public void handleCreateAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateAnnouncement.fxml");   
    }
    
    
    /**
     * Navigates to Change Password page
     * @param event ActionEvent
     * @throws IOException 
     */
    @FXML
    public void handleSettingsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminChangePassword.fxml");   
    }
}
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedUserLbl.textProperty().bind(LoginStorage.getInstance().usernameProperty());
    } 
    
    
    /**
     * Navigates to Create User page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createAccountBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateUser.fxml");
    }
    
    
    /**
     * Navigates to Create Package page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createPackageBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "CreatePackage.fxml");   
    }
    
    
    /**
     * Navigates to View Subscriptions page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void viewSubscriptionsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminViewSubscriptions.fxml");   
    } 
    
    
    /**
     * Navigates to Create Schedule page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createScheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateSchedule.fxml");   
    }
    
    
    /**
     * Navigates to Create Announcement page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateAnnouncement.fxml");   
    }
    
    
    /**
     * Navigates to Chat page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void chatBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminChat.fxml");   
    }
}
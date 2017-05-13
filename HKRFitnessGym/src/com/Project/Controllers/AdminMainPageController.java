package com.Project.Controllers;

import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminMainPageController implements Initializable {
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
        helper.navigateScene(event, "CreateUserPage.fxml");
    }
    
    
    /**
     * Navigates to Create Package page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createPackageBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "CreatePackagePage.fxml");   
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
        helper.navigateScene(event, "CreateSchedulePage.fxml");   
    }
    
    
    /**
     * Navigates to Create Announcement page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateAnnouncementPage.fxml");   
    }
    
    
    /**
     * Navigates to Chat page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void chatBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminChatPage.fxml");   
    }
    
    
}

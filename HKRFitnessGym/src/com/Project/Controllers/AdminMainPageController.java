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
     * Navigate to Create User page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void accountBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateUserPage.fxml");
    }
    
    
    /**
     * Navigate to Create Package page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void packageBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "CreatePackagePage.fxml");   
    }
    
    
    /**
     * Navigate to Create Schedule page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void scheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateSchedulePage.fxml");   
    }
    
    
    /**
     * Navigate to Create Announcement page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void announcementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateAnnouncementPage.fxml");   
    }
   
    
    /**
     * Navigate to Chat page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void chatBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "ChatPage.fxml");   
    }
    
    
    /**
     * Navigate to View Subscriptions page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void subscribeBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminViewSubscriptions.fxml");   
    } 
}
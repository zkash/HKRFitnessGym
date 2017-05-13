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
     * Navigate to Create User page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void packageBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/CreatePackagePage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
        
    }
    @FXML
    public void scheduleBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/CreateSchedulePage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
    @FXML
    public void announcementBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/CreateAnnouncementPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
   
    @FXML
    public void chatBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/AdminChatPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
    @FXML
    public void subscribeBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/AdminViewMemberAccounts.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
    public void createAccountBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateUserPage.fxml");
    }
    @FXML
    public void settingsBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/AdminSettingsPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
    
    
    /**
     * Navigate to Create Package page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createPackageBtnClick(ActionEvent event) throws IOException {
      helper.navigateScene(event, "CreatePackagePage.fxml");   
    }
    
    
    /**
     * Navigate to Create Schedule page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createScheduleBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateSchedulePage.fxml");   
    }
    
    
    /**
     * Navigate to Create Announcement page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void createAnnouncementBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "CreateAnnouncementPage.fxml");   
    }
    
    /**
     * Navigate to View Subscriptions page
     * @param event
     * @throws IOException 
     */
    @FXML
    public void viewSubscriptionsBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminViewSubscriptions.fxml");   
    } 
}

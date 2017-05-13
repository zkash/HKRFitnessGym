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
public class MemberMainPageController implements Initializable {
    @FXML Label loggedUserLbl;
    Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedUserLbl.textProperty().bind(LoginStorage.getInstance().usernameProperty());
        
    }   
    
    @FXML
    private void accountBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/UpdateMemberPersonalInformationPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }  
    
    @FXML
    private void packageBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/UpdatePackageInformationPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }  
    
    @FXML
    private void chatBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberChatPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }  
    
    @FXML
    private void settingsBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberChangePassword.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
    
    @FXML
    private void scheduleBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberViewSchedule.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }  
    
    @FXML
    private void announcementBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberViewAnnouncement.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } 
    @FXML
    private void subscribeBtnClick(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberViewPackages.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }  
}

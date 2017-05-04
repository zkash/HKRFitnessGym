/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

//import com.Project.JDBC.DAO.DBHandler;
//import com.Project.JDBC.DTO.Person;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 *
 * @author KN
 */
public class SettingsPageController implements Initializable {
    @FXML
    private Label errorMessage;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void savePassword(ActionEvent event){

        
    }
    
    @FXML
    private void goToMemberMainPage(ActionEvent event) throws IOException {
    
    }
//    @FXML
//    private void goToMemberMainPage(ActionEvent event) throws IOException {
//      Node node = (Node) event.getSource();
//      Stage stage = (Stage) node.getScene().getWindow();
//      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberMainPage.fxml"));
//
//       // if old password is belong to the logged user.
//        String oldPass = "";
//        for (Person person : DBHandler.getMemberList("SELECT * FROM person"
//                + " WHERE idPerson =" + DBHandler.getLoggedUserId())) {
//            oldPass = person.getPassword();
//        }
//        if (oldPassword.getText().equals(oldPass)) {
//            // If password length is correct.
//            if (newPassword.getText().length() < 5 || newPassword.getText().length() >= 10) {
//                errorMessage.setText("New password must be between 6 and 10 characters.");
//            }
//            else if (newPassword.getText().equals(oldPass)) {
//                errorMessage.setText("New password must be different than old password");
//            }
//            // Update password using DBHandler method.
//            else {
//                DBHandler.updatePassword(newPassword.getText());
//                errorMessage.setText("Your password has been changed.");
//            }
//        }
//        // If old password is incorrect error message will occure.
//        else {
//            errorMessage.setText("Old password is incorrect.");
//        }
//    }
  
    @FXML
    private void goToMenuPage(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MenuPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
}

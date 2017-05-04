/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.JDBC.DAO.DBHandler;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author KN
 */
public class LoginPageController implements Initializable {
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private Button exit;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginPageErrorMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBHandler.connect();

    }       
   @FXML
    private void handleLogin(ActionEvent event) throws IOException {
       int memberId = 0;
       try{
           memberId = DBHandler.login(userNameField.getText(), passwordField.getText());
           //It shows that credentials entered are not correct.
           if(memberId == 0){
               loginPageErrorMessage.setText("Please enter correct Username or password.");
         } else if(DBHandler.getLoggedUserPosition().equals("Pending")){
             loginPageErrorMessage.setText("Please wait! Your account needs an aproval.");
         }
           // If username and password is correct go to main manu page.
         else if(memberId >= 1){
             goToMenuPage(event);
         }
         } catch(Exception e){
             e.printStackTrace();
       }
       
    }



        //String uname = userNameField.getText();
        //String pwd = passwordField.getText();
        
        //Check if username and password belongs to admin or members
        //TODO remove the hard-coded and change it from the database
        
       /* if(uname.equals("a") && pwd.equals("a")) {
            visitAdminPage(event);
        }
        else {
            visitMemberPage(event);
        }
        
    }*/

    // Exit the application.
    @FXML 
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
    
    /*private void visitAdminPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/AdminMainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void visitMemberPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberMainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/
    
    @FXML
    private void goToRegisterPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/RegisterPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    // Change scene to menu page.
    private void goToMenuPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MenuPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();      
    }
    
}

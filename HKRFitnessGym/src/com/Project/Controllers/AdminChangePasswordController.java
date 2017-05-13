/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

//import com.Project.JDBC.DAO.DBhandler;
import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 *
 * @author shameer
 */
public class AdminChangePasswordController implements Initializable {
    @FXML
    private Label errorMessage;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;

    
    private int id =  LoginStorage.getInstance().getId();
    private String accountType = LoginStorage.getInstance().getAccountType();
    private DBHandler dbHandler = new DBHandler();
    private Helper helper = new Helper();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void savePassword(ActionEvent event) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String oldPasswordFromDB = dbHandler.getPassword(id, accountType);
        System.out.println("heresave");
        String enteredOldPassword = oldPassword.getText();
        String hashedEnteredOldPassword = helper.hash(enteredOldPassword);
        if(hashedEnteredOldPassword.equals(oldPasswordFromDB)) {
            String enteredNewPassword = newPassword.getText();
            String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
                   
            if (!enteredNewPassword.matches(pwRegex)) {
                errorMessage.setText("New password must be at least 5 characters, a digit is required");
            }
            else if (newPassword.getText().equals(enteredOldPassword)) {
                errorMessage.setText("New password must be different than old password");
            }
           //  Update password using DBHandler method.
            else {
                String hashedNewPassword = helper.hash(enteredNewPassword);
                System.out.println("has " + hashedNewPassword);
                dbHandler.updatePassword(accountType, id, hashedNewPassword);
                errorMessage.setText("Your password has been changed.");
            }
        }
        
        
        
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
  
}
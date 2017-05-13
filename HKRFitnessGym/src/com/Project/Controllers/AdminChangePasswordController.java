/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

//import com.Project.JDBC.DAO.DBhandler;
import com.Project.Models.DBHandler;
import com.Project.Models.LoginStorage;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 *
 * @author KN
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
    private DBHandler dbhandler = new DBHandler();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void savePassword(ActionEvent event) throws SQLException {
        String oldPwd = dbhandler.getOldPassword(id);
        if((oldPassword.getText()).equals(oldPwd)) {
            if (newPassword.getText().length() < 5 || newPassword.getText().length() >= 10) {
                errorMessage.setText("New password must be between 5 and 10 characters.");
            }
            else if (newPassword.getText().equals(oldPwd)) {
                errorMessage.setText("New password must be different than old password");
            }
           //  Update password using DBHandler method.
            else {
                dbhandler.updatePassword(accountType, id, newPassword.getText());
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
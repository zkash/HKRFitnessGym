/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
public class MemberChangePasswordController implements Initializable {

    @FXML
    private Label errorMessage;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;  
    private int id = LoginStorage.getInstance().getId();
    private String accountType = LoginStorage.getInstance().getAccountType();
    private DBHandler dbHandler = new DBHandler();
    private Helper helper = new Helper();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    private void savePassword(ActionEvent event) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String oldPasswordFromDB = dbHandler.getPassword(id, accountType);
        System.out.println("heresave");
        String enteredOldPassword = oldPassword.getText();
        String hashedEnteredOldPassword = helper.hash(enteredOldPassword);
        if (hashedEnteredOldPassword.equals(oldPasswordFromDB)) {
            String enteredNewPassword = newPassword.getText();
            String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars

            if (!enteredNewPassword.matches(pwRegex)) {
                errorMessage.setText("New password must be at least 5 characters, a digit is required");
            } else if (newPassword.getText().equals(enteredOldPassword)) {
                errorMessage.setText("New password must be different than old password");
            } //  Update password using DBHandler method.
            else {
                String hashedNewPassword = helper.hash(enteredNewPassword);
                System.out.println("has " + hashedNewPassword);
                dbHandler.updatePassword(accountType, id, hashedNewPassword);
                errorMessage.setText("Your password has been changed.");
            }
        }

    }
    
}

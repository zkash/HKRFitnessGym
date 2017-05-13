/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
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
    private void savePassword(ActionEvent event) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        String enteredOldPassword = oldPassword.getText();
        String enteredNewPassword = newPassword.getText();
        boolean changedPassword = helper.checkOldPasswordAndChangePassword(id, accountType, enteredOldPassword, enteredNewPassword);
        if(changedPassword) {
            helper.navigateScene(event, "MemberMainPage.fxml");
        }

    }
    
}

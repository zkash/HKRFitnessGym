package com.Project.Controllers;

import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminChangePasswordController implements Initializable {
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;

    private final Helper helper = new Helper();
    
    private final int id = LoginStorage.getInstance().getId();
    private final String accountType = LoginStorage.getInstance().getAccountType();
   
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    
    /**
     * Checks for old password and saves the new password
     * @param event ActionEvent
     * @throws SQLException 
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    @FXML
    private void handleChangeBtnClick(ActionEvent event) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {   
        String enteredOldPassword = oldPassword.getText();
        String enteredNewPassword = newPassword.getText();
        boolean changedPassword = helper.checkOldPasswordAndChangePassword(id, accountType, enteredOldPassword, enteredNewPassword);
        if(changedPassword) {
            helper.navigateScene(event, "AdminMainMenu.fxml");
        }
    }
}
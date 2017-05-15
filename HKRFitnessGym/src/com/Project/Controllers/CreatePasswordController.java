package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreatePasswordController implements Initializable {
    @FXML private TextField password1;
    @FXML private TextField password2;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /**
     * Handles create password button click
     * @param event
     * @throws SQLException 
     * @throws java.security.NoSuchAlgorithmException 
     */
    public void handleCreateBtnClick(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        String pwd1 = password1.getText();
        String pwd2 = password2.getText();
        
        //Set values in LoginStorage
        String accountType = LoginStorage.getInstance().getAccountType();
        int id = LoginStorage.getInstance().getId();
        
        String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$";   //minimum 1 alphabet, 1 number, 5 characters
        
        if(pwd1.matches(pwRegex) && pwd2.matches(pwRegex))
        {            
            if(pwd1.equals(pwd2)) {
                try {
                    String hashedPassword = helper.hash(pwd1);
                    dbHandler.updatePassword(accountType, id, hashedPassword);
                    helper.showDialogBox(false, "Password updated");
                    helper.navigateScene(event, "LoginPage.fxml");
                }
                catch (IOException | SQLException e) {
                    helper.showDialogBox(true, "Cannot update password");
                }
            }
            else {
                helper.showDialogBox(true, "Both passwords do not match. Please enter new password again");
            }
        }
        else {
            helper.showDialogBox(true, "Provided password is not valid.\n\nIt has to be at least 5 characters\nwith at least 1 alphabet and 1 digit.");
        }
    }
}
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void createBtnClick(ActionEvent event) throws SQLException {
        String pwd1 = password1.getText();
        String pwd2 = password2.getText();
        
        //Set values in LoginStorage
        String accountType = LoginStorage.getInstance().getAccountType();
        int id = LoginStorage.getInstance().getId();
        
        String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
        
        if(pwd1.matches(pwRegex) && pwd2.matches(pwRegex))
        {            
            if(pwd1.equals(pwd2)) {
                try {
                    dbHandler.updatePassword(accountType, id, pwd1);
                    helper.showDialogBox(false, "Password updated");
                    helper.navigateScene(event, "/com/Project/FXML/LoginPage.fxml");
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
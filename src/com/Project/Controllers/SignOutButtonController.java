package com.Project.Controllers;

import com.Project.Models.Helper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class SignOutButtonController implements Initializable {
    Helper helper = new Helper();
    
    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    
    /**
     * Handles the sign out button click and redirects to the Login page
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleSignOutBtnClick(ActionEvent event) throws IOException {
        helper.navigateScene(event, "LoginPage.fxml");
    } 
}
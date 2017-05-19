package com.Project.Controllers;

import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class HomeButtonController {
    private final String accountType = LoginStorage.getInstance().getAccountType();
    private final Helper helper = new Helper();
    
    
    /**
     * Handles the home button click and redirects to Administrator main page or Member main page depending on logged user account type
     * @param event ActionEvent
     * @throws IOException 
     */
    public void handleHomeBtnClick(ActionEvent event) throws IOException {
        if(accountType.equals("Admin")) {
            helper.navigateScene(event, "AdminMainMenu.fxml");
        }
        else if(accountType.equals("Member")) {
            helper.navigateScene(event, "MemberMainMenu.fxml");
        } 
    }
}
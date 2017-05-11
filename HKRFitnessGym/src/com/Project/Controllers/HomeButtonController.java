package com.Project.Controllers;

import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import javafx.event.ActionEvent;

/**
 *
 * @author shameer
 */
public class HomeButtonController {
    private final String accountType = LoginStorage.getInstance().getAccountType();
    private final Helper helper = new Helper();
    
    
    /**
     * Handles the home button click and redirects to Admin main page or Member main page depending on logged user account type
     * @param event
     * @throws IOException 
     */
    public void homeBtnClick(ActionEvent event) throws IOException {
        if(accountType.equals("Admin")) {
            helper.navigateScene(event, "AdminMainPage.fxml");
        }
        else if(accountType.equals("Member")) {
            helper.navigateScene(event, "MemberMainPage.fxml");
        } 
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 *
 * @author shameer
 */
public class HomeButtonController {
    private final String accountType = LoginStorage.getInstance().getAccountType();
    private final Helper helper = new Helper();
    
    public void homeBtnClick(ActionEvent event) throws IOException {
        if(accountType.equals("Admin")) {
            helper.navigateScene(event, "/com/Project/FXML/AdminMainPage.fxml");
        }
        else if(accountType.equals("Member")) {
            helper.navigateScene(event, "/com/Project/FXML/MemberMainPage.fxml");
        } 
    }
}

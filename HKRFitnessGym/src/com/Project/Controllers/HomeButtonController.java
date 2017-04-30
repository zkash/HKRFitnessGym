/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class HomeButtonController {
    private final String accountType = LoginStorage.getInstance().getAccountType();
    
    public void homeBtnClick(ActionEvent event) throws IOException {
        if(accountType.equals("Admin")) {
            Helper.navigateScene(event, "/com/Project/FXML/AdminMainPage.fxml");
        }
        else if(accountType.equals("Member")) {
            Helper.navigateScene(event, "/com/Project/FXML/MemberMainPage.fxml");
        } 
    }
}

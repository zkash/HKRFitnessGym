/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminMainPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML Label loggedUserLbl;
    
    private int adminSSN;
    private boolean login;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminSSN = loginStatus.getSSN();
        login = loginStatus.getLogin();
       
    }    
    
    public void accountBtnClick(ActionEvent event) throws IOException {
    }
    
    public void packageBtnClick(ActionEvent event) throws IOException {
    }
    
    public void scheduleBtnClick(ActionEvent event) throws IOException {
    }
    
    public void announcementBtnClick(ActionEvent event) throws IOException {
    }
    
    public void setUsername(String uname) {
        System.out.println("hoohaa");
        this.loggedUserLbl.setText(uname);
    }
    
    public void setSSN(int ssn) {
        this.adminSSN = ssn;
        //this.loggedUserLbl.setText(Integer.toString(ssn));
    }
}

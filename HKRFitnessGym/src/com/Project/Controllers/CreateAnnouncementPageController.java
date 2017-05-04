/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateAnnouncementPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField announcementTitle;
    @FXML private TextArea announcementBody;
    @FXML private Label invalidMsgAnnouncement;
    @FXML private Label invalidMsgAllData;
    
    private Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void createAnnouncementBtnClick(ActionEvent event) {
        //Clear error message
        invalidMsgAnnouncement.setText("");
        
        String at = announcementTitle.getText();
        String ab = announcementBody.getText();
        if(helper.isEmpty(at) || helper.isEmpty(ab)) {
            invalidMsgAllData.setText("Enter All Values");
        }
    }  
}

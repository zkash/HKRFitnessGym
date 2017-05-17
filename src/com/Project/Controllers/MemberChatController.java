/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author KN
 */
public class MemberChatController implements Initializable {
    @FXML
    private TextArea messageArea;
    @FXML
    private TextArea message;
    @FXML
    private Label errorMessage;
    @FXML
    private Button enter;
    
    //private int id =  LoginStorage.getInstance().getId();
    //private String accountType = LoginStorage.getInstance().getAccountType();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    @FXML
    private void saveMessage(ActionEvent event) {
        Date date = new Date();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy.MM.dd 'at' H:mm ");
        try {
            // Checks if field is empty.
            if (message.getText().length() == 0) {
                errorMessage.setText("Please write your message.");
            }
            // Checks the lenght of the message.
            else if (message.getText().length() >= 100) {
                errorMessage.setText("Maximum 100 characters");
                message.clear();
            }
            // If none of conditions apply, message will be saved.
            else if (message.getText().length() <= 100 && message.getText().length() > 0) {
                //DBHandler.saveMessage(datef.format(date), DBHandler.getLoggedUser(), message.getText());
                message.clear();
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.Chat;
import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author KN
 */
public class AdminChatController implements Initializable {
    @FXML
    private TextArea messageArea;
    @FXML
    private TextArea text;
    @FXML
    private Label errorMessage;
    @FXML
    private Button enter;
    
    //private int id =  LoginStorage.getInstance().getId();
    private String accountType = LoginStorage.getInstance().getAccountType();
    private final Helper helper = new Helper();
    private final DBHandler dbHandler = new DBHandler();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
  }
    @FXML
    private void saveMessage(ActionEvent event) {
        //Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd 'at' H:mm ");
        java.sql.Date date = helper.getCurrentDateInSqlDate();
        System.out.println("DATE " + date);
        LocalTime currentTime= helper.getCurrentTime();
        System.out.println("CT " + currentTime);
        //java.sql.Date sqlDate = (java.sql.Date) date;
       // String title = titleTextField.getText();
       try{
            // It checks if field is unfilled.
            if(text.getText().length()== 0){
                errorMessage.setText("Please write your message");
            }
            // checks the length of text.
            else if(text.getText().length() >= 100){
                errorMessage.setText("Maximum 100 characters allowed");
                text.clear();
            }
            // Saves text after fulfill conditions.
            else if(text.getText().length() <= 100 && text.getText().length() > 0){
                //dbHandler.saveAnnouncement(dateformat.format(date), text.getText());
          
                String txt = text.getText();
                Chat chat = new Chat();
                chat.setDate(date);
                chat.setTime(currentTime.toString());
             // chat.setMessage(message);
             // chat.setMessageId(messageId);
              //chat.setSenderUsername(senderUsername);
             // chat.setReceiveUsername(receiveUsername);
                //dbHandler.saveMessage(chat);
                text.clear();
                
            }
            
       }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
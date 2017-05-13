/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.JDBC.DAO.DBhandler;
import com.Project.JDBC.DTO.Announcement;
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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateAnnouncementPageController implements Initializable {
    @FXML
        private TextArea messageArea, message;
        @FXML
        private Button enter;
        @FXML
        private Label errorMessage;
        
       

    /**
     * Initializes the controller class.
     */
    private DBHandler dbHandler = new DBHandler();
    private int id =  LoginStorage.getInstance().getId();
    private String accountType = LoginStorage.getInstance().getAccountType();
        
    private final Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //checkPosition();
    }    
    
    public void announcementBtnClick(ActionEvent event) {
        //Clear error message
        errorMessage.setText("");
        
        String m = message.getText();
        String ma = messageArea.getText();
        if(helper.isEmpty(m) || helper.isEmpty(ma)) {
            errorMessage.setText("Enter All Values");
        }
    }  
    @FXML
    private void saveAnnouncement(ActionEvent event){
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd 'at' H:mm ");
        try{
            // It checks if field is unfilled.
            if(message.getText().length()== 0){
                errorMessage.setText("Please write your message");
            }
            // checks the length of message.
            else if(message.getText().length() >= 100){
                errorMessage.setText("Maximum 100 characters allowed");
                message.clear();
            }
            // Saves message after fulfill conditions.
            else if(message.getText().length() <= 100 && message.getText().length() > 0){
                DBhandler.saveAnnouncement(dateformat.format(date), message.getText());
                message.clear();
                
            }
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
     // Retrieves messages from database.
    private void loadMessage() {
        messageArea.clear();
        for (Announcement announcement : DBhandler.getAnnouncementList(("SELECT * FROM announcement"))) {
            messageArea.appendText(announcement.getTime() + " : " + announcement.getMessage() + "\n");
            }
        }
    
    
    // Checks if logged user has the privileges.
  /*  private void checkPosition() {
        if (DBhandler.getId().equals("Admin")) {
            message.setVisible(true);
            enter.setVisible(true);
        }
        else {
            message.setVisible(false);
            enter.setVisible(false);
        }
    }*/
    
}

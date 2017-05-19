/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.Announcement;
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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateAnnouncementController implements Initializable {
        @FXML
        private TextArea message;
        @FXML
        private Button enter;
        @FXML
        private Label errorMessage;
        @FXML
        private TextField titleTextField;
        
        private final String username = LoginStorage.getInstance().getUsername();
        private final int adminId = LoginStorage.getInstance().getId();
        boolean saveAnnouncement = false;
       
    /**
     * Initializes the controller class.
     */
  
     private final Helper helper = new Helper();
     private final DBHandler dbHandler = new DBHandler();
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //checkPosition();
    }    
    /**
     * Handles save announcement into database
     * @param event ActionEvent
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    private void saveAnnouncement(ActionEvent event){
        //Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' H:mm ");
        java.sql.Date date = helper.getCurrentDateInSqlDate();
        System.out.println("DATE " + date);
        LocalTime currentTime= helper.getCurrentTime();
        System.out.println("CT " + currentTime);
        //java.sql.Date sqlDate = (java.sql.Date) date;
        String title = titleTextField.getText();
        try{
            // It checks if field is unfilled.
            if(message.getText().length()== 0){
                //errorMessage.setText("Please write your message");
                helper.showDialogBox(true, "Please write your message first.");
            }
            // checks the length of message.
            else if(message.getText().length() >= 300){
                //errorMessage.setText("Maximum 300 characters allowed");
                //message.clear();
                helper.showDialogBox(true, "Maximum 300 characters allowed.");
            }
            // Saves message after fulfill conditions.
            else if(message.getText().length() <= 100 && message.getText().length() > 0){
                //dbHandler.saveAnnouncement(dateformat.format(date), message.getText());
                String body = message.getText();
                Announcement announcement = new Announcement();
                announcement.setDate(date);
                //announcement.setTime(time);
                announcement.setTime(currentTime.toString());
                announcement.setTitle(title);
                announcement.setBody(body);
                announcement.setUsername(username);
                announcement.setAdminId(adminId);
                dbHandler.saveAnnouncement(announcement);
                helper.showDialogBox(true, "Announcement updated");
                helper.navigateScene(event, "AdminViewAnnouncement.fxml");
                      
          }    
        }   catch (Exception e){
            e.printStackTrace();
        }
    }
}

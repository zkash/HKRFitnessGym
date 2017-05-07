/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Controllers.DBHandler;
import com.Project.JDBC.DTO.Announcements;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author KN
 */
public class AnnouncementsPageController implements Initializable {
        @FXML
        private TextArea messageArea, message;
        @FXML
        private Button enter;
        @FXML
        private Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       checkPosition();
       loadMessage();
    }
    @FXML
    private void saveMessage(ActionEvent event){
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd 'at' H:mm ");
        try{
            // It checks if field is unfilled.
            if(message.getText().length()== 0){
                errorMessage.setText("Please write your message");
            }
            // checks the length of message.
            else if(message.getText().length() >= 50){
                errorMessage.setText("Maximum 50 characters allowed");
                message.clear();
            }
            // Saves message after fulfill conditions.
            else if(message.getText().length() <= 50 && message.getText().length() > 0){
                DBHandler.saveAnnouncement(dateformat.format(date), message.getText());
                message.clear();
                loadMessage();
            }
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    // It checks the position of logged user.
    private void checkPosition(){
        if (DBHandler.getLoggedUserPosition().equals("Admin")) {
            message.setVisible(true);
            enter.setVisible(true);
        }
        else {
            message.setVisible(false);
            enter.setVisible(false);
        }
    }

    // To retrieve message from database.
        private void loadMessage(){
            messageArea.clear();
            for (Announcements announcements : DBHandler.getAnnouncementsList("SELECT * FROM announcements")){
                messageArea.appendText(announcements.getTime() + " : " + announcements.getMessage() + "\n");
               }
            }
     
        @FXML
      private void goToMenuPage(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MenuPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
}
    


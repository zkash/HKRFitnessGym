/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void createAnnouncementBtnClick(ActionEvent event) {
        //Clear error message
        invalidMsgAnnouncement.setText("");
        
        String at = announcementTitle.getText();
        String ab = announcementBody.getText();
        if(isEmpty(at) || isEmpty(ab)) {
            invalidMsgAnnouncement.setText("Enter All Values");
        }
    }
    
    public void backBtnClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MenuPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public boolean isEmpty(String str) {
        if(str  == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
    
}

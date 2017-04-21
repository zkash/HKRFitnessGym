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
import javafx.stage.Stage;

/**
 *
 * @author KN
 */
public class ChatPageController implements Initializable {
    
    @FXML
    private TextArea messageArea;
    @FXML
    private TextArea message;
    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    @FXML
    private void saveMessage(ActionEvent event){
        
        
        
    }
    
    @FXML
    private void goToMemberMainPage(ActionEvent event) throws IOException {
      Node node = (Node) event.getSource();
      Stage stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberMainPage.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
        
}

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class AdminViewSubscriptionRequestsController implements Initializable {

    @FXML private Pane acceptRequestPane;
    @FXML private Pane declineRequestPane;
    @FXML private TextField offerPriceTextField;
    @FXML private TextArea declineMessageTextArea;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acceptRequestPane.setVisible(false);
        declineRequestPane.setVisible(false);
    }    
    
    public void acceptRequestBtnClick(ActionEvent event) {
        declineRequestPane.setVisible(false);
        acceptRequestPane.setVisible(true);
    }
    
    public void declineRequestBtnClick(ActionEvent event) {
        acceptRequestPane.setVisible(false);
        declineRequestPane.setVisible(true);
    }
    
    public void acceptSendBtnClick(ActionEvent event) {
        String offerPrice = offerPriceTextField.getText();
        
    }
    
    public void declineSendBtnClick(ActionEvent event) {
        String declineMessage = declineMessageTextArea.getText();
    }
}

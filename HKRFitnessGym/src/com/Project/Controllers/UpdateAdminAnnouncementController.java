/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author KN
 */
public class UpdateAdminAnnouncementController implements Initializable {
    
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea message;
    
    private String title;
    private String body;
    private String time;
    private Date date;
    
    private String oldAnnouncement;
    private String newAnnouncement;
    
    private AdminViewAnnouncementController adminViewAnnouncementController;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    /**
     * Handles update button click
     * @param event ActionEvent
     * @throws SQLException 
     */
    public void handleUpdateBtnClick(ActionEvent event) throws SQLException, IOException {
       // Node node = (Node) event.getSource();
        //Stage stage = (Stage) node.getScene().getWindow();
        
       
    }
}
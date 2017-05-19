/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.Announcement;
import java.io.IOException;
import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author KN
 */
public class UpdateAdminAnnouncementController implements Initializable {
    @FXML private TextArea message;
    @FXML private TextField titleTextField;
    
    private List<TextField> textField;
    private List<TextArea> textArea;
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    
    private AdminViewAnnouncementController adminViewAnnouncementController;
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    Announcement oldAnnouncement = new Announcement();
     /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String oldTitle = titleTextField.getText();        
        textField = Arrays.asList(titleTextField);
        
        String oldMessage = message.getText();        
        textArea = Arrays.asList(message);    
        
    }   
    /**
     * Handles update button click
     * @param event ActionEvent
     * @throws SQLException 
     */
    public void handleUpdateBtnClick(ActionEvent event) throws SQLException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' H:mm ");
        java.sql.Date date = helper.getCurrentDateInSqlDate();
        System.out.println("DATE " + date);
        
        LocalTime time= helper.getCurrentTime();
        System.out.println("CT " + time);
        //java.sql.Date sqlDate = (java.sql.Date) date;
        String title = titleTextField.getText();
        String body = message.getText();
        
        Announcement announcement = new Announcement();
        announcement.setDate(date);
        //announcement.setTime(time);
        announcement.setTime(time.toString());
        announcement.setTitle(title);
        announcement.setBody(body);
        //announcement.setAdminId(adminId);
        dbHandler.updateAnnouncement(oldAnnouncement, announcement);
        helper.showDialogBox(true, "Announcement updated.");
        helper.navigateScene(event, "AdminViewAnnouncement.fxml");
        message.clear();
}
    
    /**
     * Sets values retrieved from database into respective graphical elements
     */
     public void setAnnouncement(String title, String body, Date date, String time) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        titleTextField.setText(title);
        message.setText(body);
        oldAnnouncement.setTitle(title);
         System.out.println("1");
        oldAnnouncement.setBody(body);
        oldAnnouncement.setDate(date);
        oldAnnouncement.setTime(time);
    }
    
}   
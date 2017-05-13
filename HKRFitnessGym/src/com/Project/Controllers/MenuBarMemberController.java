package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MenuBarMemberController implements Initializable {
    @FXML MenuBar menuBarMember;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }    
    
    
    public void viewAccountDetailsMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("MemberViewPersonalInformation.fxml");
    }
    
    
    public void subscribeToPackageMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewPackages.fxml");
    }
    
    public void viewSubscriptionsMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewSubscriptions.fxml");
    }
    
    
    public void viewScheduleMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewSchedule.fxml");
    }
    
    public void viewAnnouncementMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberViewAnnouncement.fxml");
    }
    
    public void visitChatRoomMenuClick(ActionEvent event) throws IOException {
        setupAndShowStage("MemberChat.fxml");
    }

    
    
    public void setupAndShowStage(String fxmlFile) throws IOException {
        Stage stage = (Stage) menuBarMember.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/Views/" + fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Navigates to Update Personal Information page
     * @param event
     * @throws IOException 
     */
    public void updatePersonalInfoMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("UpdateMemberPersonalInformation.fxml");
    }
    
    public void changePasswordMenuClick(ActionEvent event) throws IOException {
       setupAndShowStage("MemberChangePassword.fxml");
    }
    
}

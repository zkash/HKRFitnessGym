/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreatePackagePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Label invalidMsgPackageName;
    @FXML private Label invalidMsgPackageCost;
    @FXML private Label invalidMsgPackageStartTime;
    @FXML private Label invalidMsgPackageDuration;
    @FXML private TextField packageName;
    @FXML private TextField packageCost;
    @FXML private DatePicker packageStartDate;
    @FXML private DatePicker packageEndDate;    
    @FXML private TextField packageStartTime;
    @FXML private ComboBox packageStartTimeState;
    @FXML private TextField packageDuration;
    
    @FXML private Label lbl;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ObservableList<String> timeList = FXCollections.observableArrayList("AM", "PM");
        //packageStartTimeState.getItems().addAll(timeList);
        packageStartTimeState.getItems().addAll("AM", "PM");
    }    
    
    public void createPackageBtnClick(ActionEvent event) {
        //Clear error messages
        invalidMsgPackageName.setText("");
        invalidMsgPackageCost.setText("");
        invalidMsgPackageStartTime.setText("");
        invalidMsgPackageDuration.setText("");
        
        String pn = packageName.getText();
        String pc = packageCost.getText();
        LocalDate psd = packageStartDate.getValue();
//        LocalDate ped = packageEndDate.getValue();
        String pst = packageStartTime.getText();
        //String psts = packageStartTimeState.getSelectionModel().getSelectedItem().toString();
        String pd = packageDuration.getText();
//        
//        lbl.setText(pn + " " + pc + " " + psd + " " + ped + " " + pst + " " + psts + " " + pd);
        
        lbl.setText(pn + " " + pc + " " + pst + " " + pd);
//        if(pn != null && pc != null && psd != null && ped != null && pst != null & psts != null && pd != null) {
        if(pn != null && pc != null && pst != null && pd != null) {
            if(hasChar(pc)) {
                invalidMsgPackageCost.setText("Invalid Value");
            }
            
            String startTimeRegex = "^(([1-9]{1})|([1][1-2])):[0-5]{1}[0-9]{1}$";
            if(!pst.matches(startTimeRegex)) {
                invalidMsgPackageStartTime.setText("Invalid Value");
            }
            
            //String durationRegex = "^(([0-9])|([1][0-9])|([2][0-4])).$";
            try {
                int pdInt = Integer.parseInt(pd);
                System.out.println(pdInt);
                
                if((pdInt <= 0) && (pdInt >= 1440)) {
                    invalidMsgPackageDuration.setText("Invalid Value");       
                }
                else
                {
                    invalidMsgPackageDuration.setText("What the");
                }
            }
            catch (NumberFormatException e) {
                invalidMsgPackageDuration.setText("Invalid Value"); 
            }   
        }
        else {
            invalidMsgPackageName.setText("Enter All Values");
        }
    }
        
    //Check if a string contains a character
    public boolean hasChar(String str) {
        if(!str.isEmpty()) {
            char[] charArray;
            charArray = str.toCharArray();
            for(char ch: charArray) {
                if (Character.isLetter(ch)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    public void backBtnClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MenuPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

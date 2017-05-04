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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateSchedulePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML DatePicker scheduleDate;
    @FXML TextField openingTime;
    @FXML ComboBox openingTimeState;
    @FXML TextField closingTime;
    @FXML ComboBox closingTimeState;
    @FXML CheckBox isHoliday;
    @FXML Label invalidMsgOpeningTime;
    @FXML Label invalidMsgClosingTime;
    @FXML private Label invalidMsgAllData;
    @FXML Button createScheduleBtn;
    
    private final Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openingTimeState.getItems().addAll("AM", "PM");
        closingTimeState.getItems().addAll("AM", "PM");
        
    }    
    
    public void createScheduleBtnClick(ActionEvent event) {
        //Clear error messages
        invalidMsgOpeningTime.setText("");
        invalidMsgClosingTime.setText("");
        
        String ot = openingTime.getText();  
        String ct = closingTime.getText();
        
        if(helper.isEmpty(ot) || helper.isEmpty(ct) || scheduleDate == null) {
            invalidMsgAllData.setText("Enter All Values");
        }
        else {
            invalidMsgAllData.setText("");
            String openingTimeRegex = "(([1-9])|([1][1-2])):[0-5][0-9]";
            if(!ot.matches(openingTimeRegex)) {
                invalidMsgOpeningTime.setText("Invalid Value");
            }
            
            String closingTimeRegex = "(([1-9])|([1][1-2])):[0-5][0-9]";
            if(!ct.matches(closingTimeRegex)) {
                invalidMsgClosingTime.setText("Invalid Value");
            }
        }
    }
}

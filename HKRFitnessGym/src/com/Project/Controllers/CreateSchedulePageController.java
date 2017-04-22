/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import classees.Schedule;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;;
import java.text.SimpleDateFormat;
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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateSchedulePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private String oh;
    private String ch;
    private String om,cm;
    private String ot,ct;
    private String openingTimeRegex;
    private String closingTimeRegex;
    private SimpleDateFormat sdf;
    private Date d1,d2;
    
    private Schedule schedule;
    
    @FXML private DatePicker scheduleDate;
    @FXML private TextField openingTime;
    @FXML private TextField openingMin;
    @FXML private ComboBox openingTimeState;
    @FXML private TextField closingTime;
    @FXML private TextField closingMin;
    @FXML private ComboBox closingTimeState;
    @FXML private CheckBox isHoliday;
    @FXML private Label invalidMsgOpeningTime;
    @FXML private Label invalidMsgClosingTime;
    @FXML private Label invalidMsgAnnouncement;
    @FXML private Label invalidMsgAllData;
    @FXML private Button createScheduleBtn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openingTimeState.getItems().addAll("AM", "PM");
        closingTimeState.getItems().addAll("AM", "PM");
        scheduleDate.setEditable(false);
    }    
    
    public void createScheduleBtnClick(ActionEvent event) throws SQLException {        
        oh = openingTime.getText();
        ch = closingTime.getText();
        om = openingMin.getText();
        cm = closingMin.getText();
        
        try {
            checkBox();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void checkBox() throws Exception{
        //choose date part
        if(scheduleDate.getValue() == null) {
            invalidMsgAnnouncement.setText("Pick Up A Date");
        }
        // opening time box
        else if(!Helper.isInteger(oh) || !Helper.isInteger(om)) {
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgOpeningTime.setText("Invalid Time");
        }
        else if((Integer.valueOf(oh)<1) || (Integer.valueOf(oh)>12)){
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgOpeningTime.setText("Out Of Range");
        }
        else if((Integer.valueOf(om)<0) || (Integer.valueOf(om)>59)){
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgOpeningTime.setText("Out Of Range");
        }
        
        else if(closingTimeState.getSelectionModel().isEmpty() || openingTimeState.getSelectionModel().isEmpty()){
                invalidMsgAnnouncement.setText("");
                invalidMsgAllData.setText("Choose parts of day.");
        }
        
        //closing time
        else if(!Helper.isInteger(ch) || !Helper.isInteger(cm)) {
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("Invalid Time");
        }
        else if((Integer.valueOf(ch)<1) || (Integer.valueOf(ch)>12)){
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("Out Of Range");
        }
        else if((Integer.valueOf(cm)<0) || (Integer.valueOf(cm)>59)){
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("Out Of Range");
        }
        
        else if(isMorning(openingTimeState) && isMorning(closingTimeState)){
            timeFormat();
            if(d1.after(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }   
        }
        else if(!isMorning(openingTimeState) && !isMorning(closingTimeState)){
            timeFormat();
            if(d1.after(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }   
        }
        
        else if(isMorning(closingTimeState) && !isMorning(openingTimeState)){
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("Invalid Time");
        }
        
        else if(isMorning(closingTimeState) && isMorning(openingTimeState)){
            timeFormat();
            if(d1.equals(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }
        }
        else if(!isMorning(closingTimeState) && !isMorning(openingTimeState)){
            timeFormat();
            if(d1.equals(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }
        }
        
        else{
            timeFormat();
            //clear text
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("");
            invalidMsgOpeningTime.setText("");
            
            //insert date to data base.
            Time opening = new Time(d1.getTime());
            Time closing = new Time(d2.getTime());
            DBHandler.adminCreateSchedule(Helper.convertDate(scheduleDate.getValue()),opening, closing, isHoliday.isSelected(),1234567);
        }
    }
    
    
    public void mouseChecked(MouseEvent e){
        if(openingTime.getText().isEmpty()){
            openingTime.setText("12");
        }
        else if(openingMin.getText().isEmpty()){
            openingMin.setText("00");
        }
        else if(closingTime.getText().isEmpty()){
            closingTime.setText("12");
        }
        else if(closingMin.getText().isEmpty()){
            closingMin.setText("00");
        }
    }
    
    public void timeFormat() throws ParseException{
        ot = oh + ":" + om;
        ct = ch + ":" + cm;
        sdf = new SimpleDateFormat("HH:mm");
        
        d1 = sdf.parse(ot);
        d2 = sdf.parse(ct);
        
    }
    
    public boolean isMorning(ComboBox cb){
        if(cb.getSelectionModel().getSelectedItem().equals("AM")){
            return true;
        }
        else if(cb.getSelectionModel().getSelectedItem().equals("PM")){
            return false;
        }
        return true;
    }
}

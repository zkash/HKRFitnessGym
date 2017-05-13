/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.JDBC.DAO.DBhandler;
import com.Project.JDBC.DTO.Schedule;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;import java.text.SimpleDateFormat;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    //create schedule---------------------------
    private String oh;
    private String ch;
    private String om,cm;
    private String ot;
    private String ct; 
    private String openingTimeRegex;
    private String closingTimeRegex;
    private SimpleDateFormat sdf;
    private Date d1,d2;
    private String newD1,newD2;//they are 24 hour format.
    
    private Schedule schedule = new Schedule();
    
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
        checkHoliday();
        
        //choose date part
        if(scheduleDate.getValue() == null) {
            invalidMsgAnnouncement.setText("Pick Up A Date");
        }
        else if(scheduleDate.getValue().isBefore(currentDate())){
            invalidMsgAnnouncement.setText("Invalid Date");
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
            else if(d1.equals(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }
            else{
                saveToDatabase();
            }
        }
        else if(!isMorning(openingTimeState) && !isMorning(closingTimeState)){
            timeFormat();
            
            if(d1.after(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }
            else if(d1.equals(d2)){
                invalidMsgAllData.setText("");
                invalidMsgAnnouncement.setText("");
                invalidMsgClosingTime.setText("Invalid Time");
            }
            else{
                saveToDatabase();
            }
        }
        else if(isMorning(closingTimeState) && !isMorning(openingTimeState)){
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("Invalid Time");
        }
        else{
            saveToDatabase();
        }
    }
    
    private void saveToDatabase() throws SQLException, ParseException{
        setDate();
            
            timeFormat();
            schedule.setOpeningTime(newD1);
            schedule.setClosingTime(newD2);
            
            //clear text
            invalidMsgAllData.setText("");
            invalidMsgAnnouncement.setText("");
            invalidMsgClosingTime.setText("");
            invalidMsgOpeningTime.setText("");
            
            //insert date to data base.
            //System.out.println(Helper.toSQLDate(schedule.getDate()));
            System.out.println(schedule.getOpeningTime());
            System.out.println(schedule.getClosingTime());
            System.out.println(schedule.getIsHoliday());
            DBhandler.adminCreateSchedule(schedule.getDate(),schedule.getOpeningTime(), schedule.getClosingTime(), schedule.getIsHoliday(),1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("DATA HAS BEEN SAVED.");
            alert.show();
    }
    
    private void setDate(){
        schedule.setDate(Helper.toSQLDate(scheduleDate.getValue()));
    }
    
    private void checkHoliday(){
        schedule.setIsHoliday(isHoliday.isSelected());
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
        ot = oh + ":" + om + " " + openingTimeState.getSelectionModel().getSelectedItem();
        ct = ch + ":" + cm + " " + closingTimeState.getSelectionModel().getSelectedItem();
        sdf = new SimpleDateFormat("hh:mm a");
        
        d1 = sdf.parse(ot);
        d2 = sdf.parse(ct);
        
        //HH for hour of the day (0 - 23)
        DateFormat sdf2 = new SimpleDateFormat("HH:mm");
        newD1 = sdf2.format(d1);
        newD2 = sdf2.format(d2);
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
    
    private LocalDate currentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        return localDate;
    }
}
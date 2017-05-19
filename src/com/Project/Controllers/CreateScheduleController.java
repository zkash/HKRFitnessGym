/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.Schedule;
import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateScheduleController implements Initializable {

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
    
    DBHandler db = new DBHandler();
    private Schedule schedule = new Schedule();
    private Helper help = new Helper();
    
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
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    
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
            check();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void check() throws Exception{
        checkHoliday();
        
        //choose date part
        
        if(scheduleDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Pick Up A Date");
            alert.show();
            //invalidMsgAnnouncement.setText("Pick Up A Date");
        }
        else if(scheduleDate.getValue().isBefore(currentDate())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid Date");
            alert.show();
            //invalidMsgAnnouncement.setText("Invalid Date");
        }
        else if(!db.exisitDate(scheduleDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Date already in database");
            alert.show();
            //invalidMsgAnnouncement.setText("Date already in database");
        }
        // opening time box
        else if(!help.isInteger(oh) || !help.isInteger(om)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid Time");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgOpeningTime.setText("Invalid Time");
        }
        else if((Integer.valueOf(oh)<1) || (Integer.valueOf(oh)>12)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Out Of Range");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgOpeningTime.setText("Out Of Range");
        }
        else if((Integer.valueOf(om)<0) || (Integer.valueOf(om)>59)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Out Of Range");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgOpeningTime.setText("Out Of Range");
        }
        
        else if(closingTimeState.getSelectionModel().isEmpty() || openingTimeState.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Choose state of day");
            alert.show();
            //invalidMsgAnnouncement.setText("");
            //invalidMsgAllData.setText("Choose parts of day.");
        }
        
        //closing time
        else if(!help.isInteger(ch) || !help.isInteger(cm)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid Time");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgClosingTime.setText("Invalid Time");
        }
        else if((Integer.valueOf(ch)<1) || (Integer.valueOf(ch)>12)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Out Of Range");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgClosingTime.setText("Out Of Range");
        }
        else if((Integer.valueOf(cm)<0) || (Integer.valueOf(cm)>59)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Out Of Range");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgClosingTime.setText("Out Of Range");
        }
        else if(isMorning(openingTimeState) && isMorning(closingTimeState)){
            
            timeFormat();
            if(d1.after(d2)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Invalid Time");
                alert.show();
                //invalidMsgAllData.setText("");
                //invalidMsgAnnouncement.setText("");
                //invalidMsgClosingTime.setText("Invalid Time");
            }
            else if(d1.equals(d2)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Invalid Time");
                alert.show();
                //invalidMsgAllData.setText("");
                //invalidMsgAnnouncement.setText("");
                //invalidMsgClosingTime.setText("Invalid Time");
            }
            else{
                saveToDatabase();
            }
        }
        else if(!isMorning(openingTimeState) && !isMorning(closingTimeState)){
            timeFormat();
            
            if(d1.after(d2)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Invalid Time");
                alert.show();
                //invalidMsgAllData.setText("");
                //invalidMsgAnnouncement.setText("");
                //invalidMsgClosingTime.setText("Invalid Time");
            }
            else if(d1.equals(d2)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Invalid Time");
                alert.show();
                //invalidMsgAllData.setText("");
                //invalidMsgAnnouncement.setText("");
                //invalidMsgClosingTime.setText("Invalid Time");
            }
            else{
                saveToDatabase();
            }
        }
        else if(isMorning(closingTimeState) && !isMorning(openingTimeState)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid Time");
            alert.show();
            //invalidMsgAllData.setText("");
            //invalidMsgAnnouncement.setText("");
            //invalidMsgClosingTime.setText("Invalid Time");
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
            
            db.adminCreateSchedule(schedule, adminId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("DATA HAS BEEN SAVED.");
            alert.show();
    }
    
    private void setDate(){
        schedule.setDate(help.convertLocalDateToSQLDate(scheduleDate.getValue()));
    }
    
    private void checkHoliday(){
        schedule.setIsHoliday(isHoliday.isSelected());
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

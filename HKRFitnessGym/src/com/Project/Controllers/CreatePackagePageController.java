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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.Project.Controllers.Helper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;

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
    @FXML private Label invalidMsgPackageEndTime;
    @FXML private TextField packageName;
    @FXML private TextField packageCost;
    @FXML private DatePicker packageStartDate;
    @FXML private DatePicker packageEndDate;    
    @FXML private TextField packageStartTime;
    @FXML private ComboBox packageStartTimeState;
    @FXML private ComboBox packageEndTimeState;
    @FXML private TextField packageEndTime;
    @FXML private Label invalidMsgAllData;
   
    private List<TextField> textfields;
    private List<Label> labels;
    private List<String> validationChecks;
    
    private BooleanBinding validated;
    private Package pack;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ObservableList<String> timeList = FXCollections.observableArrayList("AM", "PM");
        //packageStartTimeState.getItems().addAll(timeList);
        packageStartTimeState.getItems().addAll("AM", "PM");
        packageEndTimeState.getItems().addAll("AM", "PM");
        
        textfields = Arrays.asList(packageName, packageCost, packageStartTime, packageEndTime);
        labels = Arrays.asList(invalidMsgPackageName, invalidMsgPackageCost, invalidMsgPackageStartTime, invalidMsgPackageEndTime);
        validationChecks = Arrays.asList("[a-zA-Z0-9]*", "[0-9]*|([0-9]*\\.[0-9]{1,2})", "([1-9]|[1][0-2]):[0-5][0-9]", "([1-9]|[1][0-2]):[0-5][0-9]");
    
        //Add listeners to the textfields
        IntStream.range(0, textfields.size()).forEach(i -> {
            textfields.get(i).focusedProperty().addListener((observable, oldProperty, newProperty) -> {
                if(!textfields.get(i).getText().isEmpty() && !textfields.get(i).getText().matches(validationChecks.get(i))) {
                    labels.get(i).setText("Invalid Value");
                }
                else {
                    labels.get(i).setText("");
                }
            });
        });
        
        //Boolean binding true when textfields are filled and labels are empty
        validated = new BooleanBinding() {
            
            //Bind TextProperty of labels and textfields to the boolean binding
            {
                super.bind(labels.stream().map(label -> label.textProperty()).toArray(Observable[]::new));
                super.bind(textfields.stream().map(textField -> textField.textProperty()).toArray(Observable[]::new));
            }
            
            @Override
            protected boolean computeValue() {
                //Get the value to return by checking textfields and labels
                return textfields.stream().allMatch(textField -> !textField.getText().isEmpty()) && labels.stream().allMatch(label -> label.getText().isEmpty());
          }
        };
    }    
    
    public void createPackageBtnClick(ActionEvent event) throws SQLException {
        //Clear error messages
        invalidMsgPackageName.setText("");
        invalidMsgPackageCost.setText("");
        invalidMsgPackageStartTime.setText("");
        invalidMsgPackageEndTime.setText("");
        
        String pn = packageName.getText();
        String pc = packageCost.getText();
        String pst = packageStartTime.getText();
        String pet = packageEndTime.getText();
        LocalDate psd = packageStartDate.getValue();
        LocalDate ped = packageEndDate.getValue();
       
        int admin_ssn = 1234567890;
        
        if (validated.get()) {
            int count = DBHandler.checkPackageName(pn);
            boolean alreadyExists;
            System.out.println(count);
            if (count == 0) {
                alreadyExists = false;
                
                //Get AM/PM text
                String psts = (String)packageStartTimeState.getValue();
                String pets = (String)packageEndTimeState.getValue();
                
                System.out.println(psts);
                System.out.println(pets);
                
                if (psd.compareTo(ped) > 0) {   //Start date is earlier than end date
                    Helper.DialogBox(alreadyExists, "End date cannot be earlier than start date");
                }
                else {
                
                    if(psts.equals("PM")) {
                        pst = Helper.convertTimeTo24HourFormat(pst);
                    }

                    if(pets.equals("PM")) {
                        pet = Helper.convertTimeTo24HourFormat(pet);
                    }

                    if((psts.equals("AM") && pets.equals("AM")) || (psts.equals("PM") && pets.equals("PM")) || (psts.equals("PM") && pets.equals("AM"))) {
                        //End time before start time
                        if (convertTimeToMinuteSinceMidnight(pst) > convertTimeToMinuteSinceMidnight(pet)) {
                            Helper.DialogBox(alreadyExists, "Start time cannot be earlier than end time");
                            Helper.clearTextField(packageStartTime, packageEndTime);
                        }
                        else {
                            pack = new Package(pn, Float.valueOf(pc), Helper.convertLocalDateToSQLDate(psd), Helper.convertLocalDateToSQLDate(ped), pst, pet);
                            insertIntoDB(pack, admin_ssn, alreadyExists);
                        }
                    }
                    else if (psts.equals("AM") && pets.equals("PM")) {
                        pack = new Package(pn, Float.valueOf(pc), Helper.convertLocalDateToSQLDate(psd), Helper.convertLocalDateToSQLDate(ped), pst, pet);
                        insertIntoDB(pack, admin_ssn, alreadyExists);
                    }
                }
            }
            else {
                alreadyExists = true;
                Helper.DialogBox(alreadyExists, "Package with same name already exists");
                Helper.clearTextField(packageName);
            }
        }
        else {
            Helper.DialogBox(true, "Enter all data");
        }
    }
    
    public int convertTimeToMinuteSinceMidnight(String time) {
        String[] timeDivided = time.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        int minute = Integer.parseInt(timeDivided[1]);
        int minutesSinceMidnight = (hour * 60) + minute;
        return minutesSinceMidnight;
    }
    
    public void insertIntoDB(Package pack, int admin_ssn, boolean alreadyExists) throws SQLException {
        DBHandler.createPackage(pack, admin_ssn);
        Helper.clearTextField(packageName, packageCost, packageStartTime, packageEndTime);
        packageStartDate.getEditor().clear();
        packageEndDate.getEditor().clear();
        Helper.DialogBox(alreadyExists, "Package successfully created");
    }
}

        //MY CODE
//        if(!Helper.isEmpty(pn) && !Helper.isEmpty(pc) && !Helper.isEmpty(pst) && !Helper.isEmpty(pd)) {
//            if(Helper.hasChar(pc)) {
//                invalidMsgPackageCost.setText("Invalid Value");
//            }
//            else {
//                String costRegex = "^[0-9]*.[0-9]{1,2}$";
//                if(!pc.matches(costRegex)) {
//                    invalidMsgPackageCost.setText("Invalid Value");
//                }
//                else {
//                    //price less than 0
//                    float pcInt = Float.parseFloat(pc);
//                    if(pcInt < 0) {    
//                        invalidMsgPackageCost.setText("Invalid Value");
//                    }
//                }
//            }
//            
//            String startTimeRegex = "^(([1-9]{1})|([1][1-2])):[0-5]{1}[0-9]{1}$";
//            if(!pst.matches(startTimeRegex)) {
//                invalidMsgPackageStartTime.setText("Invalid Value");
//            }
//        }
//        else {
//            invalidMsgAllData.setText("Enter All Data");
//        }  
 

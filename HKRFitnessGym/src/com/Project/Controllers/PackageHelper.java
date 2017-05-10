package com.Project.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author shameer
 */
public class PackageHelper {
    String packageNameOld;
    String packageName;
    String packageCost;
    String packageStartTime;
    String packageEndTime;
    LocalDate packageStartDate;
    LocalDate packageEndDate;

    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private BooleanBinding validated;
    public BooleanBinding addListenerBindTextFieldsAndLabels(List<TextField> textfields, List<Label> labels, List<String> validationChecks) {
        
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
        return validated;
    }
    
    
    /**
     * Handles the button click - Create Package Button click or Update Package Button click
     * @param todo Task to do - Create Package or Update Package
     * @param packName Old package name in case of Update Package, copy of package name in case of Create Package
     * @param event Button click ActionEvent
     * @param textFieldList An array of text fields in the scene
     * @param datePickerList An array of date pickers in the scene
     * @param comboBoxList An array of combo boxes in the scene
     * @param stage Current stage
     * @param adminId Admin's Id from LoginStorage
     * @throws SQLException
     * @throws IOException 
     */
    public void btnClick(String todo, String packName, ActionEvent event, ArrayList<TextField> textFieldList, ArrayList<DatePicker> datePickerList, ArrayList<ComboBox> comboBoxList, Stage stage, int adminId) throws SQLException, IOException {
        Package pack;
        packageNameOld = packName;
        packageName = textFieldList.get(0).getText();
        packageCost = textFieldList.get(1).getText();
        packageStartTime = textFieldList.get(2).getText();
        packageEndTime = textFieldList.get(3).getText();
        packageStartDate = datePickerList.get(0).getValue();
        packageEndDate = datePickerList.get(1).getValue();
        
        //Get AM/PM text
        String packageStartTimeState = (String)comboBoxList.get(0).getValue();
        String packageEndTimeState = (String)comboBoxList.get(1).getValue();

        if (packageStartDate.compareTo(packageEndDate) > 0) {   //Start date is earlier than end date
            helper.showDialogBox(true, "End date cannot be earlier than start date");
        }
        else {
            if(packageStartTimeState.equals("PM")) {
                packageStartTime = helper.convertTimeTo24HourFormat(packageStartTime);
            }

            if(packageEndTimeState.equals("PM")) {
                packageEndTime = helper.convertTimeTo24HourFormat(packageEndTime);
            }

            if((packageStartTimeState.equals("AM") && packageEndTimeState.equals("AM")) || 
                    (packageStartTimeState.equals("PM") && packageEndTimeState.equals("PM")) || 
                    (packageStartTimeState.equals("PM") && packageEndTimeState.equals("AM"))) {
                //End time before start time
                if (convertTimeToMinuteSinceMidnight(packageStartTime) > convertTimeToMinuteSinceMidnight(packageEndTime)) {
                    helper.showDialogBox(true, "Start time cannot be earlier than end time");
                    helper.clearTextField(textFieldList.get(1), textFieldList.get(2));
                }
                else {
                    pack = new Package(
                            packageName, 
                            Float.valueOf(packageCost), 
                            helper.convertLocalDateToSQLDate(packageStartDate), 
                            helper.convertLocalDateToSQLDate(packageEndDate), 
                            packageStartTime, 
                            packageEndTime);
                    handleTask(todo, packageNameOld, event, stage, pack, adminId, textFieldList, datePickerList);
                }
            }
            else if (packageStartTimeState.equals("AM") && packageEndTimeState.equals("PM")) {
                pack = new Package(
                        packageName, 
                            Float.valueOf(packageCost), 
                            helper.convertLocalDateToSQLDate(packageStartDate), 
                            helper.convertLocalDateToSQLDate(packageEndDate), 
                            packageStartTime, 
                            packageEndTime);
                handleTask(todo, packageNameOld, event, stage, pack, adminId, textFieldList, datePickerList);
            }
        }
    }
    
    
    /**
     * Sends the package object to handler to store in database
     * @param todo Task to do - Create Package or Update Package
     * @param packageNameOld Old package name in case of Update Package,  copy of package name in case of Create Package
     * @param event Button click ActionEvent
     * @param stage Current stage
     * @param pack Package class object
     * @param adminId Admin's Id from LoginStorage
     * @param textFieldList An array of text fields in the scene
     * @param datePickerList An array of date pickers in the scene
     * @throws SQLException
     * @throws IOException 
     */
    public void handleTask(String todo, String packageNameOld, ActionEvent event, Stage stage, Package pack, int adminId, ArrayList<TextField> textFieldList, ArrayList<DatePicker> datePickerList) throws SQLException, IOException {
        if(todo.equals("Create")) {
            dbHandler.createPackage(pack, adminId);
            
            //Clear text fields 
            textFieldList.forEach((textField) -> {
                helper.clearTextField(textField);
            });

            //Clear date pickers 
            datePickerList.forEach((datePicker) -> {
                datePicker.getEditor().clear();
            });
        
            helper.showDialogBoxChoice(stage, "Package successfully created", "Do you want to create another package?", "/com/Project/FXML/AdminViewPackages.fxml");
        }
        else if(todo.equals("Update")) {
            dbHandler.updatePackage(pack, packageNameOld, adminId);
            
            //Clear text fields 
            textFieldList.forEach((textField) -> {
                helper.clearTextField(textField);
            });

            //Clear date pickers 
            datePickerList.forEach((datePicker) -> {
                datePicker.getEditor().clear();
            });
            
            helper.showDialogBox(true, "Package information successfully updated");
            helper.navigateScene(event, "/com/Project/FXML/AdminViewPackages.fxml");
        } 
    }
    
    
    /**
     * Converts given time to number of minutes since midnight
     * @param time Time to convert into number of minutes
     * @return 
     */
    public int convertTimeToMinuteSinceMidnight(String time) {
        String[] timeDivided = time.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        int minute = Integer.parseInt(timeDivided[1]);
        int minutesSinceMidnight = (hour * 60) + minute;
        return minutesSinceMidnight;
    }
}
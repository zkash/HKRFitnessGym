/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class UpdatePackageInformationPageController implements Initializable {

    private AdminViewPackagesController adminViewPackagesController;
    private ObservableList<Package> pack;
    
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void updateBtnClick(ActionEvent event) {
        
    }
    
    public void setFields(ObservableList<Package> pack) {
        System.out.println(pack);
    }
    
    public void injectAdminViewPackagesController(AdminViewPackagesController adminViewPackagesController) {
        this.adminViewPackagesController = adminViewPackagesController;
    }
    
    public void setPackage(ObservableList<Package> pack) {
        packageName.setText(pack.get(0).getPackageName());
        packageCost.setText(Float.toString(pack.get(0).getPrice()));
        packageStartDate.setValue(Helper.convertSQLDateToLocalDate(pack.get(0).getStartDate())); 
        packageEndDate.setValue(Helper.convertSQLDateToLocalDate(pack.get(0).getEndDate()));
        ArrayList<String> startTime = Helper.convertTimeTo12HourFormat(pack.get(0).getStartTime());
        ArrayList<String> endTime = Helper.convertTimeTo12HourFormat(pack.get(0).getEndTime());
        
        packageStartTime.setText(startTime.get(0));     //set time in 12 hour format
        packageEndTime.setText(endTime.get(0));
        
        if(startTime.get(1) == "PM") {
            packageStartTimeState.setValue("PM");
        }
        
        if(endTime.get(1) == "PM") {
            packageEndTimeState.setValue("PM");
        }
    }
}

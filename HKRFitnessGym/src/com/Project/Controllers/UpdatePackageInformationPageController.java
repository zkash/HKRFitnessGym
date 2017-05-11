/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.Package;
import com.Project.Models.PackageHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    
    private List<TextField> textfields;
    private List<Label> labels;
    private List<String> validationChecks;
    
    private BooleanBinding validated;
    private Package updatedPackage;
    
    private String packageNameOld;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    private final PackageHelper packageHelper = new PackageHelper();
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        packageNameOld = packageName.getText();
        
        textfields = Arrays.asList(packageName, packageCost, packageStartTime, packageEndTime);
        labels = Arrays.asList(invalidMsgPackageName, invalidMsgPackageCost, invalidMsgPackageStartTime, invalidMsgPackageEndTime);
        validationChecks = Arrays.asList("[a-zA-Z0-9]*", "[0-9]*|([0-9]*\\.[0-9]{1,2})", "([1-9]|[1][0-2]):[0-5][0-9]", "([1-9]|[1][0-2]):[0-5][0-9]");
    
        validated = packageHelper.addListenerBindTextFieldsAndLabels(textfields, labels, validationChecks);
    }
    
    public void updateBtnClick(ActionEvent event) throws SQLException, IOException {
       
        
        String pn = packageName.getText();
        String pc = packageCost.getText();
        String pst = packageStartTime.getText();
        String pet = packageEndTime.getText();
        LocalDate psd = packageStartDate.getValue();
        LocalDate ped = packageEndDate.getValue();
       
        int admin_ssn = 1234567890;
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        
        if (validated.get()) {
            ArrayList<TextField> textFieldList = new ArrayList<>();
                textFieldList.add(packageName);
                textFieldList.add(packageCost);
                textFieldList.add(packageStartTime);
                textFieldList.add(packageEndTime);
                
                ArrayList<DatePicker> datePickerList = new ArrayList<>();
                datePickerList.add(packageStartDate);
                datePickerList.add(packageEndDate);
                
                ArrayList<ComboBox> comboBoxList = new ArrayList<>();
                comboBoxList.add(packageStartTimeState);
                comboBoxList.add(packageEndTimeState);
                
                String todo = "Update";
                packageHelper.btnClick(todo, packageNameOld, event, textFieldList, datePickerList, comboBoxList, stage, adminId);
        }
//            //Get AM/PM text
//            String psts = (String)packageStartTimeState.getValue();
//            String pets = (String)packageEndTimeState.getValue();
//            
//            if (psd.compareTo(ped) > 0) {   //Start date is earlier than end date
//                    helper.showDialogBox(true, "End date cannot be earlier than start date");
//            }
//                else {
//                
//                    if(psts.equals("PM")) {
//                        pst = helper.convertTimeTo24HourFormat(pst);
//                    }
//
//                    if(pets.equals("PM")) {
//                        pet = helper.convertTimeTo24HourFormat(pet);
//                    }
//
//                    if((psts.equals("AM") && pets.equals("AM")) || (psts.equals("PM") && pets.equals("PM")) || (psts.equals("PM") && pets.equals("AM"))) {
//                        //End time before start time
//                        if (convertTimeToMinuteSinceMidnight(pst) > convertTimeToMinuteSinceMidnight(pet)) {
//                            helper.showDialogBox(true, "Start time cannot be earlier than end time");
//                            helper.clearTextField(packageStartTime, packageEndTime);
//                        }
//                        else {
//                            updatedPackage = new Package(pn, Float.valueOf(pc), helper.convertLocalDateToSQLDate(psd), helper.convertLocalDateToSQLDate(ped), pst, pet);
//                            updateInDB(updatedPackage, admin_ssn, false);
//                        }
//                    }
//                    else if (psts.equals("AM") && pets.equals("PM")) {
//                        updatedPackage = new Package(pn, Float.valueOf(pc), helper.convertLocalDateToSQLDate(psd), helper.convertLocalDateToSQLDate(ped), pst, pet);
//                        updateInDB(updatedPackage, admin_ssn, false);
//                    }
//                }
//        }
//        else {
//            helper.showDialogBox(true, "Enter all data");
//        }
//    }
    }
    
    public void setFields(ObservableList<Package> pack) {
        System.out.println(pack);
    }
    
    public void injectAdminViewPackagesController(AdminViewPackagesController adminViewPackagesController) {
        this.adminViewPackagesController = adminViewPackagesController;
    }
    
    public void setPackage(ObservableList<Package> pack) {
        packageName.setText(pack.get(0).getPackageName());
        packageNameOld = pack.get(0).getPackageName();
        packageCost.setText(Float.toString(pack.get(0).getPrice()));
        packageStartDate.setValue(helper.convertSQLDateToLocalDate(pack.get(0).getStartDate())); 
        packageEndDate.setValue(helper.convertSQLDateToLocalDate(pack.get(0).getEndDate()));
        ArrayList<String> startTime = helper.convertTimeTo12HourFormat(pack.get(0).getStartTime());
        ArrayList<String> endTime = helper.convertTimeTo12HourFormat(pack.get(0).getEndTime());
        
        packageStartTime.setText(startTime.get(0));     //set time in 12 hour format
        packageEndTime.setText(endTime.get(0));
        
        if("PM".equals(startTime.get(1))) {
            packageStartTimeState.setValue("PM");
        }
        
        if("PM".equals(endTime.get(1))) {
            packageEndTimeState.setValue("PM");
        }
    }
    
    public int convertTimeToMinuteSinceMidnight(String time) {
        String[] timeDivided = time.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        int minute = Integer.parseInt(timeDivided[1]);
        int minutesSinceMidnight = (hour * 60) + minute;
        return minutesSinceMidnight;
    }
    
    public void updateInDB(Package pack, int admin_ssn, boolean alreadyExists) throws SQLException {
        dbHandler.updatePackage(pack, packageNameOld, admin_ssn);
        helper.clearTextField(packageName, packageCost, packageStartTime, packageEndTime);
        packageStartDate.getEditor().clear();
        packageEndDate.getEditor().clear();
        helper.showDialogBox(alreadyExists, "Package information successfully updated");
    }
}
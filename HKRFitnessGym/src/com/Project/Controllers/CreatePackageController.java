package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.PackageHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.BooleanBinding;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreatePackageController implements Initializable {
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
   
    private List<TextField> textfields;
    private List<Label> labels;
    private List<String> validationChecks;
    
    private BooleanBinding validated;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    private final PackageHelper packageHelper = new PackageHelper();
    
    private final int adminId = LoginStorage.getInstance().getId();

    /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        packageStartTimeState.getItems().addAll("AM", "PM");
        packageEndTimeState.getItems().addAll("AM", "PM");
        
        textfields = Arrays.asList(packageName, packageCost, packageStartTime, packageEndTime);
        labels = Arrays.asList(invalidMsgPackageName, invalidMsgPackageCost, invalidMsgPackageStartTime, invalidMsgPackageEndTime);
        validationChecks = Arrays.asList("[a-zA-Z0-9 ]*", "[0-9]*|([0-9]*\\.[0-9]{1,2})", "([1-9]|[1][0-2]):[0-5][0-9]", "([1-9]|[1][0-2]):[0-5][0-9]");
    
        validated = packageHelper.addListenerBindTextFieldsAndLabels(textfields, labels, validationChecks);
    }    
    
    
    /**
     * Handles create package button click
     * @param event ActionEvent
     * @throws SQLException
     * @throws IOException 
     */
    public void handlePackageBtnClick(ActionEvent event) throws SQLException, IOException {   
        String pn = packageName.getText();

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                    
        if (validated.get()) {
            int count = dbHandler.checkPackageName(pn);
            
            if (count == 0) {
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
                
                String todo = "Create";
                packageHelper.handleButtonClick(todo, pn, event, textFieldList, datePickerList, comboBoxList, stage, adminId);
            }
            else {              
                helper.showDialogBox(true, "Package with same name already exists");
                helper.clearTextField(packageName);
            }
        }
        else {
            helper.showDialogBox(true, "Enter all data");
        }
    }
}
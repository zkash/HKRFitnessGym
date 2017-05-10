package com.Project.Controllers;

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
    private Package pack;
    
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
        packageStartTimeState.getItems().addAll("AM", "PM");
        packageEndTimeState.getItems().addAll("AM", "PM");
        
        textfields = Arrays.asList(packageName, packageCost, packageStartTime, packageEndTime);
        labels = Arrays.asList(invalidMsgPackageName, invalidMsgPackageCost, invalidMsgPackageStartTime, invalidMsgPackageEndTime);
        validationChecks = Arrays.asList("[a-zA-Z0-9]*", "[0-9]*|([0-9]*\\.[0-9]{1,2})", "([1-9]|[1][0-2]):[0-5][0-9]", "([1-9]|[1][0-2]):[0-5][0-9]");
    
        validated = packageHelper.addListenerBindTextFieldsAndLabels(textfields, labels, validationChecks);
    }    
    
    
    /**
     * Handles create package button click
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    public void createPackageBtnClick(ActionEvent event) throws SQLException, IOException {
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
       
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                    
        if (validated.get()) {
            int count = dbHandler.checkPackageName(pn);
            boolean alreadyExists;
            
            if (count == 0) {
                alreadyExists = false;
                
                //Get AM/PM text
                String psts = (String)packageStartTimeState.getValue();
                String pets = (String)packageEndTimeState.getValue();
                
                if (psd.compareTo(ped) > 0) {   //Start date is earlier than end date
                    helper.showDialogBox(alreadyExists, "End date cannot be earlier than start date");
                }
                else {
                    if(psts.equals("PM")) {
                        pst = helper.convertTimeTo24HourFormat(pst);
                    }

                    if(pets.equals("PM")) {
                        pet = helper.convertTimeTo24HourFormat(pet);
                    }

                    if((psts.equals("AM") && pets.equals("AM")) || 
                            (psts.equals("PM") && pets.equals("PM")) || 
                            (psts.equals("PM") && pets.equals("AM"))) {
                        //End time before start time
                        if (convertTimeToMinuteSinceMidnight(pst) > convertTimeToMinuteSinceMidnight(pet)) {
                            helper.showDialogBox(alreadyExists, "Start time cannot be earlier than end time");
                            helper.clearTextField(packageStartTime, packageEndTime);
                        }
                        else {
                            pack = new Package(
                                    pn, 
                                    Float.valueOf(pc), 
                                    helper.convertLocalDateToSQLDate(psd), 
                                    helper.convertLocalDateToSQLDate(ped), 
                                    pst, 
                                    pet);
                            insertIntoDB(stage, pack, adminId, alreadyExists);
                        }
                    }
                    else if (psts.equals("AM") && pets.equals("PM")) {
                        pack = new Package(
                                pn, 
                                Float.valueOf(pc), 
                                helper.convertLocalDateToSQLDate(psd), 
                                helper.convertLocalDateToSQLDate(ped), 
                                pst, 
                                pet);
                        insertIntoDB(stage, pack, adminId, alreadyExists);
                    }
                }
            }
            else {
                alreadyExists = true;
                helper.showDialogBox(alreadyExists, "Package with same name already exists");
                helper.clearTextField(packageName);
            }
        }
        else {
            helper.showDialogBox(true, "Enter all data");
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
    
    
    /**
     * Sends the package object to handler to store in database
     * @param stage
     * @param pack
     * @param adminId
     * @param alreadyExists
     * @throws SQLException
     * @throws IOException 
     */
    public void insertIntoDB(Stage stage, Package pack, int adminId, boolean alreadyExists) throws SQLException, IOException {
        dbHandler.createPackage(pack, adminId);
        helper.clearTextField(packageName, packageCost, packageStartTime, packageEndTime);
        packageStartDate.getEditor().clear();
        packageEndDate.getEditor().clear();
        helper.showDialogBoxChoice(stage, "Package successfully created", "Do you want to create another package?", "/com/Project/FXML/AdminViewPackages.fxml");
    }
}
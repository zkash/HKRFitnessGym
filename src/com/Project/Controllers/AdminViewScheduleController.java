/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import com.Project.Models.DBHandler;
import com.Project.Models.Schedule;
import com.sun.javafx.collections.ElementObservableListDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */


public class AdminViewScheduleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField search;
    
    @FXML private TableView adminViewScheduleTable; 
    @FXML private TableColumn<Schedule, String> dateView; 
    @FXML private TableColumn<Schedule, String>otView;
    @FXML private TableColumn<Schedule, String>ctView;
    @FXML private TableColumn<Schedule, String>holidayView;
    
    private ObservableList<Schedule> data;
    private ObservableList<Schedule> searchData;
    @FXML
    private TableColumn<?, ?> edit;
    @FXML
    private TableColumn<?, ?> delete;
    
    private DBHandler dbHandler = new DBHandler();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getScheduleDetial();
        
    }

    private void getScheduleDetial() {
        try {
            
            data = FXCollections.observableArrayList();
            ResultSet rs = dbHandler.adminRitriveSchedule();
            
            while(rs.next()){
                System.out.println(rs.getDate("date"));
                System.out.println(rs.getTime("openingTime"));
                System.out.println(rs.getTime("closingTime"));
                System.out.println(rs.getBoolean("isHoliday"));
                
                data.add(new Schedule(rs.getDate("date"), rs.getString("openingTime"), rs.getString("closingTime"), rs.getBoolean("isHoliday")));
            }
            
            
            
        } catch (SQLException ex) {
            System.out.println("Error "+ ex);
        }
        dateView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("date"));
        otView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("openingTime"));
        ctView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("closingTime"));
        holidayView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("isHoliday"));
        
        adminViewScheduleTable.setItems(data);
    }
    
    private void getSearchedDetial(){
        try {
            DBHandler db = new DBHandler();
            dateView.getColumns().clear();
            otView.getColumns().clear();
            ctView.getColumns().clear();
            holidayView.getColumns().clear();
            
            searchData = FXCollections.observableArrayList();
            ResultSet rs = db.searchSchedule(search.getText());
            
            while(rs.next()){
                searchData.add(new Schedule(rs.getDate("date"), rs.getString("openingTime"), rs.getString("closingTime"), rs.getBoolean("isHoliday")));
            }
            
        } catch (SQLException ex) {
            System.out.println("Error "+ ex);
        }
        
        dateView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("date"));
        otView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("openingTime"));
        ctView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("closingTime"));
        holidayView.setCellValueFactory(new PropertyValueFactory<Schedule,String>("isHoliday"));
        
        adminViewScheduleTable.setItems(searchData);
    }
//    
    public void handleUpdateBtnClick(ActionEvent event){
        
    }
    
    @FXML
    public void handleSearchBtnClick(ActionEvent event){
        System.out.println("S " + search.getText());
        if(search.getText().isEmpty()){
            getScheduleDetial();
        }
        else{
            getSearchedDetial();
        }
    }
    
    @FXML
    public void handleDeleteBtnClick(ActionEvent event){
        ObservableList<Schedule> scheduleSelect, allSchedule;
        allSchedule = adminViewScheduleTable.getItems();
        scheduleSelect = adminViewScheduleTable.getSelectionModel().getSelectedItems();
        
        if(scheduleSelect.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please select a scheule.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure you want to delete the schedule?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
               // dbHandler.deleteSchedule(scheduleSelect.get(0).getDate());
                scheduleSelect.forEach(allSchedule::remove);
            }
            else{
                alert.close();
            }
        }
    }
    
    public void handleResetSearchBtnClick(ActionEvent event){
        
    }
    
//    @FXML
//    public void search() throws ParseException{
//        /*if(search.getText().isEmpty()){
//            getScheduleDetial();
//        }
//        else{
//            getSearchedDetial();
//        }*/
//    }
//    
//    @FXML
//    public void delete(ActionEvent event){
//        /*ObservableList<Schedule> scheduleSelect, allSchedule;
//        allSchedule = adminViewScheduleTable.getItems();
//        scheduleSelect = adminViewScheduleTable.getSelectionModel().getSelectedItems();
//        
//        if(scheduleSelect.isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error Message");
//            alert.setHeaderText("ERROR");
//            alert.setContentText("Please select a scheule.");
//            alert.showAndWait();
//        }
//        else{
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setHeaderText("Confirm Deletion");
//            alert.setContentText("Are you sure you want to delete the schedule?");
//            
//            Optional<ButtonType> result = alert.showAndWait();
//            if(result.get() == ButtonType.OK){
//                DBHandler.deleteSchedule(scheduleSelect.get(0).getDate());
//                scheduleSelect.forEach(allSchedule::remove);
//            }
//            else{
//                alert.close();
//            }
//        }*/
//    }
//    
//    /*@FXML
//    public void logoutButton(ActionEvent event) throws IOException{
//        Node node = (Node) event.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/LoginPage.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }*/
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.Project.JDBC.DAO.DBHandler;
import com.Project.JDBC.DTO.Schedule;
import com.sun.javafx.collections.ElementObservableListDecorator;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML private TableColumn<Schedule, String>idView;
    
    private ObservableList<Schedule> data;
    private ObservableList<Schedule> searchData;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getScheduleDetial();
    }

    private void getScheduleDetial() {
        try {
            data = FXCollections.observableArrayList();
            ResultSet rs = DBHandler.adminRitriveSchedule();
            
            while(rs.next()){
                System.out.println(rs.getDate("date"));
                System.out.println(rs.getTime("openingTime"));
                System.out.println(rs.getTime("closeTime"));
                System.out.println(rs.getBoolean("isHoliday"));
                
                data.add(new Schedule(rs.getDate("date"), rs.getString("openingTime"), rs.getString("closeTime"), rs.getBoolean("isHoliday")));
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
            dateView.getColumns().clear();
            otView.getColumns().clear();
            ctView.getColumns().clear();
            holidayView.getColumns().clear();
            
            searchData = FXCollections.observableArrayList();
            ResultSet rs = DBHandler.searchSchedule(search.getText());
            
            while(rs.next()){
                searchData.add(new Schedule(rs.getDate("date"), rs.getString("openingTime"), rs.getString("closeTime"), rs.getBoolean("isHoliday")));
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
    
    @FXML
    public void update(ActionEvent event){
        
    }
    
    @FXML
    public void search() throws ParseException{
        if(search.getText().isEmpty()){
            getScheduleDetial();
        }
        else{
            getSearchedDetial();
        }
        
        
        
       
    }
    
    @FXML
    public void delete(ActionEvent event){
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
            alert.setContentText("Are you sure you want to delete the package?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                DBHandler.deleteSchedule(scheduleSelect.get(0).getDate());
                scheduleSelect.forEach(allSchedule::remove);
            }
            else{
                alert.close();
            }
        }
    }
    
}

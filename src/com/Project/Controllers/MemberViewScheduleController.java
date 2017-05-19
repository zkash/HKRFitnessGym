/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.Schedule;
import com.Project.Models.DBHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class MemberViewScheduleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView memberViewScheduleTable; 
    @FXML private TableColumn<Schedule, String> dateView; 
    @FXML private TableColumn<Schedule, String>otView;
    @FXML private TableColumn<Schedule, String>ctView;
    @FXML private TableColumn<Schedule, String>holidayView;
    @FXML private TextField search;
    
    private ObservableList<Schedule> data;
    private ObservableList<Schedule> searchData;
    private DBHandler jdbc;
    
    private Statement stmt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getScheduleDetial();
    }

    private void getScheduleDetial() {
        jdbc = new DBHandler();
        try {
            Connection conn = jdbc.establishConnection();
            data = FXCollections.observableArrayList();
            stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT date, openingTime, closeTime, isHoliday FROM schedule");
            ResultSet rs = stmt.executeQuery("SELECT date, openingTime, closingTime, isHoliday FROM schedule");
            
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
        
        memberViewScheduleTable.setItems(data);
    }
    
    @FXML
    public void reset(ActionEvent event){
        getScheduleDetial();
    }
    
    @FXML
    public void search(ActionEvent event){
        if(search.getText().isEmpty()){
            getScheduleDetial();
        }
        else{
            getSearchedDetial();
        }
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
        
        memberViewScheduleTable.setItems(searchData);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.Project.JDBC.DAO.DBhandler;
import com.Project.JDBC.DTO.Schedule;
import com.sun.javafx.collections.ElementObservableListDecorator;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    
    @FXML private TableView adminViewScheduleTable; 
    @FXML private TableColumn<Schedule, String> dateView; 
    @FXML private TableColumn<Schedule, String>otView;
    @FXML private TableColumn<Schedule, String>ctView;
    @FXML private TableColumn<Schedule, String>holidayView;
    
    private ObservableList<Schedule> data;
    private DBhandler jdbc;
    
    private Statement stmt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbc = new DBhandler();
        try {
            Connection conn = DBhandler.establishConnection();
            data = FXCollections.observableArrayList();
            stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT date, openingTime, closeTime, isHoliday FROM schedule");
            ResultSet rs = stmt.executeQuery("SELECT date, openingTime, closeTime, isHoliday FROM schedule");
            
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
    
}

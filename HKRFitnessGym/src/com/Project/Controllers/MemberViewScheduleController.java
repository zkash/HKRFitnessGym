/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MemberViewScheduleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Schedule> memberViewScheduleTable;
    @FXML private TableColumn<Package, String> dateColumn;
    @FXML private TableColumn<Package, String> openingTimeColumn; 
    @FXML private TableColumn<Package, String> closingTimeColumn;
    @FXML private TableColumn<Package, String> isHolidayColumn;
    
    private final DBHandler dbHandler = new DBHandler();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<Schedule> schedule = dbHandler.memberViewSchedule();
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            openingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
            closingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
            isHolidayColumn.setCellValueFactory(new PropertyValueFactory<>("isHoliday"));
            memberViewScheduleTable.setItems(schedule);
        } catch (SQLException ex) {
            Logger.getLogger(MemberViewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
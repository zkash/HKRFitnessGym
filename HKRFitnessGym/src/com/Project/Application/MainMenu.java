/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Application;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Shameer
 */

public class MainMenu extends Application {
    
    // Creating a static root to pass to the controller
    
    private static BorderPane root = new BorderPane();

  
    public static BorderPane getRoot() {
        return root;
    }
  
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/BasicTemplate.fxml"));
        
        AnchorPane a1 = FXMLLoader.load(getClass().getResource("/com/Project/FXML/RegisterPage.fxml"));
        AnchorPane a2 = FXMLLoader.load(getClass().getResource("/com/Project/FXML/RegisterPage.fxml"));
        root.setTop(a1);
        root.setCenter(a2);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("HKR Fitness Gym");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("'here1");
        launch(args);
        System.out.println("he2");
    }
    
}


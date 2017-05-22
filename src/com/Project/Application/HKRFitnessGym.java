package com.Project.Application;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Launcher class of HKRFitnessGym software
 *
 * @author shameer
 */
public class HKRFitnessGym extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/Views/LoginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("HKR Fitness Gym");
        stage.setScene(scene);
        stage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
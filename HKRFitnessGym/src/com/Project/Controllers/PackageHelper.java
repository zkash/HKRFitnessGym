package com.Project.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author shameer
 */
public class PackageHelper {
 
    private BooleanBinding validated;
    public BooleanBinding addListenerBindTextFieldsAndLabels(List<TextField> textfields, List<Label> labels, List<String> validationChecks) {
        
        //Add listeners to the textfields
        IntStream.range(0, textfields.size()).forEach(i -> {
            textfields.get(i).focusedProperty().addListener((observable, oldProperty, newProperty) -> {
                if(!textfields.get(i).getText().isEmpty() && !textfields.get(i).getText().matches(validationChecks.get(i))) {
                    labels.get(i).setText("Invalid Value");
                }
                else {
                    labels.get(i).setText("");
                }
            });
        });
        
        //Boolean binding true when textfields are filled and labels are empty
        validated = new BooleanBinding() {
            
            //Bind TextProperty of labels and textfields to the boolean binding
            {
                super.bind(labels.stream().map(label -> label.textProperty()).toArray(Observable[]::new));
                super.bind(textfields.stream().map(textField -> textField.textProperty()).toArray(Observable[]::new));
            }
            
            @Override
            protected boolean computeValue() {
                //Get the value to return by checking textfields and labels
                return textfields.stream().allMatch(textField -> !textField.getText().isEmpty()) && labels.stream().allMatch(label -> label.getText().isEmpty());
          }
        };
        return validated;
    }
}

package com.example.piiaproject;

import javafx.fxml.FXML;
import com.example.piiaproject.Model.File;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileView implements Initializable {
    File file1;
    File file2;
    @FXML
    TextArea text1;
    @FXML
    TextArea text2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            file1 = new File("src/main/resources/com/example/piiaproject/text.txt");
            file2 = new File("src/main/resources/com/example/piiaproject/text2.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        text1.setText(file1.toString());
        text2.setText(file2.toString());
    }
}

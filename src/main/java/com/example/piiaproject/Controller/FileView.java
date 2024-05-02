package com.example.piiaproject.Controller;

import com.example.piiaproject.View.Interface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FileView implements Initializable {
    File file1;
    File file2;

    @FXML
    TextArea text1;
    @FXML
    TextArea text2;

    TextArea text3;
    @FXML
    Button saveButton;
    @FXML
    MenuItem saveMenuItem;
    @FXML
    MenuItem newMenuItem;
    @FXML
    MenuItem recentMenuItem;
    @FXML
    MenuItem exitFile;
    @FXML
    MenuItem aboutMenuItem;
    @FXML
    Button compareButton;
    String comparisonResult;

    private int saveCounter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        file1 = new File("src/main/resources/com/example/piiaproject/text.txt");
        file2 = new File("src/main/resources/com/example/piiaproject/text2.txt");
        try {
            text1.setText(readFileContents(file1.getAbsolutePath()));
            text2.setText(readFileContents(file2.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        text1.setEditable(false);
        newMenuItem.setOnAction(e -> {
            Interface newInterface = new Interface();
            try {
                newInterface.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        exitFile.setOnAction(e -> Platform.exit());

        saveMenuItem.setOnAction(e -> {
            try {
                saveCounter++;
                Files.copy(Paths.get(file1.getAbsolutePath()), Paths.get("src/main/resources/com/example/piiaproject/text" + saveCounter + ".txt"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(file2.getAbsolutePath()), Paths.get("src/main/resources/com/example/piiaproject/text2" + saveCounter + ".txt"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        /*compareButton.setOnAction(e -> {
            try {
                //compareTexts();
                text2.setText(comparisonResult);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });*/

        aboutMenuItem.setOnAction(e -> showHelpWindow());
        text1.setWrapText(true);
        text2.setWrapText(true);
    }
    private void showHelpWindow() {
        // Create a new Stage for the help window
        Stage helpStage = new Stage();

        // Create a Label with the help text
        Label helpLabel = new Label("This is some help text.");

        // Create a Scene with the Label
        Scene scene = new Scene(helpLabel, 200, 100);

        // Set the Scene on the Stage
        helpStage.setScene(scene);

        // Show the Stage
        helpStage.show();
    }

    private String readFileContents(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public void setTextAreaContents(String filePath1, String filePath2) {
        try {
            text1.setText(readFileContents(filePath1));
            text2.setText(readFileContents(filePath2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* private Text createText(String content, String style) {
        Text text = new Text(content);
        text.setStyle(style);
        return text;
    }

    private void compareTexts() throws IOException {
        comparisonResult = "";
        String text1 = readFileContents(file1.getAbsolutePath());
        String text2 = readFileContents(file2.getAbsolutePath());

        List<String> lines1 = List.of(text1.split("\\n"));
        List<String> lines2 = List.of(text2.split("\\n"));

        List<Text> result = new ArrayList<>();
        for (int i = 0; i < Math.max(lines1.size(), lines2.size()); i++) {
            String line1 = i < lines1.size() ? lines1.get(i) : "";
            String line2 = i < lines2.size() ? lines2.get(i) : "";
            if (line1.equals(line2)) {
                result.add(createText(line1, "-fx-fill: black;"));
            } else {
                result.add(createText(line1, "-fx-fill: red;"));
                result.add(createText(line2, "-fx-fill: green;"));
            }
            result.add(createText("\n", ""));
        }
        comparisonResult = result.toString();
    }*/

}
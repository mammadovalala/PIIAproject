package com.example.piiaproject.Controller;

import com.example.piiaproject.View.Interface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    MenuItem undoMenuItem;
    @FXML
    MenuItem redoMenuItem;
    @FXML
    MenuItem aboutMenuItem;

    private int saveCounter = 0;
    private List<File> recentFiles = new ArrayList<>();

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
        saveMenuItem.setOnAction(e -> saveTextToFile(text2, "modifiedText.txt"));
        exitFile.setOnAction(e -> Platform.exit());
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

    public void saveTextToFile(TextArea textArea, String filePath) {
        try {
            // Check if filePath is null or points to a non-existing file
            if (filePath == null || !new File(filePath).exists()) {
                System.out.println("Invalid file path: " + filePath);
                return;
            }

            // Get the directory of the reference file
            String directory = new File(filePath).getParent();

            // Generate a new file name with the counter
            String newFileName = "text" + saveCounter + ".txt";

            // Generate a new file path for the new file in the same directory
            String newFilePath = Paths.get(directory, newFileName).toString();

            // Save the text to the new file
            Files.write(Paths.get(newFilePath), textArea.getText().getBytes());

            System.out.println("Saved to " + newFilePath);

            // Increment the counter
            saveCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*private void updateRecentFilesMenu() {
        // Clear the existing menu items
        recentMenuItem.getItems().clear();

        // Add a new menu item for each recent file
        for (File file : recentFiles) {
            MenuItem menuItem = new MenuItem(file.getName());
            menuItem.setOnAction(e -> openFile(file));
            recentMenuItem.getItems().add(menuItem);
        }
    }*/

}
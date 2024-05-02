package com.example.piiaproject.Controller;

import com.example.piiaproject.Model.FileModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileController {
    private FileModel model;
    private Stage primaryStage;
    private MenuItem newMenuItem;

    public FileController(FileModel model, Stage primaryStage) {
        this.model = model;
        this.primaryStage = primaryStage;
    }

    public void setButtonActions(Button fichierRefButton, Button fichierModifButton, Button poursuivreButton) {
        fichierRefButton.setOnAction(e -> selectFile("Fichier de référence", fichierRefButton));
        fichierModifButton.setOnAction(e -> selectFile("Fichier modifié", fichierModifButton));
        poursuivreButton.setOnAction(e -> {
            try {
                compareFiles();
                openFXML();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void openFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/piiaproject/hello-view.fxml"));
        Parent root = loader.load();
        FileView fileViewController = loader.getController();
        fileViewController.setTextAreaContents(model.getFileRef().getAbsolutePath(), model.getFileModif().getAbsolutePath());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        primaryStage.close();
    }

    public void selectFile(String title, Button button) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        String userDirectoryPath = "/Users/lala/Desktop/PIIAprojet/PIIAproject/src/main/resources/com/example/piiaproject";
        File userDirectory = new File(userDirectoryPath);
        fileChooser.setInitialDirectory(userDirectory);

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            if (title.equals("Fichier de référence")) {
                model.setFileRef(selectedFile);
                button.setText(selectedFile.getName());
                System.out.println("Fichier de référence sélectionné : " + selectedFile.getAbsolutePath());
            } else if (title.equals("Fichier modifié")) {
                model.setFileModif(selectedFile);
                button.setText(selectedFile.getName());
                System.out.println("Fichier modifié sélectionné : " + selectedFile.getAbsolutePath());
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    public void compareFiles() throws IOException {
        if (model.getFileRef() != null && model.getFileModif() != null) {
            File f1 = model.getFileRef();
            File f2 = model.getFileModif();
            System.out.println("Comparaison des fichiers " + f1 + " et " + f2);
        } else {
            System.out.println("Veuillez sélectionner les fichiers de référence et modifiés.");
        }
    }

    public void saveAsNewFile() {
        model.setFileRef(model.getFileModif());
        try {
            Files.copy(model.getFileRef().toPath(), Paths.get("newFile.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.example.piiaproject.View;

import com.example.piiaproject.Controller.FileController;
import com.example.piiaproject.Model.FileModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class Interface extends Application {
    private Stage primaryStage;
    private File fichierRef;
    private File fichierModif;
    private File fichierResultat;
    private Button fichierRefButton;
    private Button fichierModifButton;
    private FileModel model;
    private FileController controller;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page d'accueil");

        model = new FileModel(fichierRef, fichierModif);
        controller = new FileController(model, primaryStage);

        // Create labels and buttons
        Label fichierRefLabel = new Label("Fichier de référence");
        fichierRefButton = new Button("Sélectionner un fichier de référence");

        Label fichierModifLabel = new Label("Fichier modifié");
        fichierModifButton = new Button("Sélectionner un fichier modifié");

        Button poursuivreButton = new Button("Poursuivre");

        // Set button actions
        controller.setButtonActions(fichierRefButton, fichierModifButton, poursuivreButton);

        // Arrange elements in a GridPane container
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Add elements to the GridPane
        gridPane.add(fichierRefLabel, 0, 0);
        gridPane.add(fichierRefButton, 1, 0);
        gridPane.add(fichierModifLabel, 0, 1);
        gridPane.add(fichierModifButton, 1, 1);
        gridPane.add(poursuivreButton, 0, 2);

        // Create a scene with the GridPane container
        Scene scene = new Scene(gridPane, 400, 300);

        // Associate the scene with the main window
        primaryStage.setScene(scene);

        // Display the main window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.piiaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button fichierRefButton;
    private Button fichierModifButton;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page d'accueil");

        // Créer les labels et les boutons
        Label fichierRefLabel = new Label("Fichier de référence");
        fichierRefButton = new Button("Sélectionner un fichier de référence");
        fichierRefButton.setOnAction(e -> selectFile("Fichier de référence"));

        Label fichierModifLabel = new Label("Fichier modifié");
        fichierModifButton = new Button("Sélectionner un fichier modifié");
        fichierModifButton.setOnAction(e -> selectFile("Fichier modifié"));

        Button poursuivreButton = new Button("Poursuivre");
        poursuivreButton.setOnAction(e -> {
            try {
                if (fichierRef == null || fichierModif == null) {
                    System.out.println("Veuillez sélectionner les fichiers de référence et modifiés.");
                    return;
                }else {
                    compareFiles();
                    primaryStage.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });

        // Disposer les éléments dans un conteneur VBox
        VBox root = new VBox();
        root.getChildren().addAll(
                new HBox(fichierRefLabel, fichierRefButton),
                new HBox(fichierModifLabel, fichierModifButton),
                poursuivreButton
        );

        // Créer une scène avec le conteneur VBox
        Scene scene = new Scene(root, 400, 300);

        // Associer la scène à la fenêtre principale
        primaryStage.setScene(scene);

        // Afficher la fenêtre principale
        primaryStage.show();
    }

    private void selectFile(String title) {
        // Créer une boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        // Spécifier le répertoire initial
        String userDirectoryPath = "/Users/lala/Desktop/PIIAprojet/PIIAproject/src/main/resources/com/example/piiaproject"; // Remplacez par le chemin du dossier souhaité
        File userDirectory = new File(userDirectoryPath);
        fileChooser.setInitialDirectory(userDirectory);

        // Afficher la boîte de dialogue et attendre que l'utilisateur sélectionne un fichier
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        // Si un fichier est sélectionné, stockez-le dans la variable appropriée
        if (selectedFile != null) {
            if (title.equals("Fichier de référence")) {
                fichierRef = selectedFile;
                fichierRefButton.setText(fichierRef.getName());
                System.out.println("Fichier de référence sélectionné : " + fichierRef.getAbsolutePath());
            } else if (title.equals("Fichier modifié")) {
                fichierModif = selectedFile;
                fichierModifButton.setText(fichierModif.getName());
                System.out.println("Fichier modifié sélectionné : " + fichierModif.getAbsolutePath());
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }


    private void compareFiles() throws IOException {
        if (fichierRef != null && fichierModif != null) {
            File f1 = new File(fichierRef.getAbsolutePath());
            File f2 = new File(fichierModif.getAbsolutePath());
            // Maintenant, vous pouvez utiliser les instances f1 et f2 pour la comparaison des fichiers
            System.out.println("Comparaison des fichiers " + f1 + " et " + f2);

            // Par exemple, ouvrir une autre fenêtre avec les fichiers de référence et modifiés
            openComparisonWindow(f1, f2);
        } else {
            System.out.println("Veuillez sélectionner les fichiers de référence et modifiés.");
        }
    }

    private void openComparisonWindow(File f1, File f2) {
        // Créer une nouvelle fenêtre
        Stage comparisonStage = new Stage();
        comparisonStage.setTitle("Textes");

        // Charger le contenu de la nouvelle fenêtre depuis un fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Parent myPane = loader.load();
            Scene myScene = new Scene(myPane, 1200, 700);

            // Définir la scène de la nouvelle fenêtre
            comparisonStage.setScene(myScene);

            // Afficher la nouvelle fenêtre
            comparisonStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package com.example.projet_piia_nouvelle_version;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class TextComparatorJavaFX extends Application {

    private TextArea textAreaLeft;
    private TextArea textAreaRight;
    private VBox comparisonResult;

    @Override
    public void start(Stage primaryStage) {
        textAreaLeft = new TextArea();
        textAreaRight = new TextArea();
        comparisonResult = new VBox(5);

        textAreaLeft.setEditable(false);
        textAreaRight.setEditable(true);

        Button loadFileButtonLeft = new Button("Charger Fichier Gauche");
        Button loadFileButtonRight = new Button("Charger Fichier Droite");
        Button compareButton = new Button("Comparer Textes");

        loadFileButtonLeft.setOnAction(e -> loadFile(textAreaLeft));
        loadFileButtonRight.setOnAction(e -> loadFile(textAreaRight));
        compareButton.setOnAction(e -> compareTexts());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(textAreaLeft, 0, 0);
        gridPane.add(textAreaRight, 1, 0);
        gridPane.add(loadFileButtonLeft, 0, 1);
        gridPane.add(loadFileButtonRight, 1, 1);
        gridPane.add(compareButton, 0, 2, 2, 1);
        gridPane.add(comparisonResult, 0, 3, 2, 1);

        Scene scene = new Scene(gridPane, 1000, 700);
        primaryStage.setTitle("Text Comparator - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFile(TextArea textArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        java.io.File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String content = Files.readString(Path.of(file.getPath()));
                textArea.setText(content);
            } catch (IOException ex) {
                System.out.println("Erreur de lecture du fichier: " + ex.getMessage());
            }
        }
    }

    private void compareTexts() {
        comparisonResult.getChildren().clear();
        String text1 = textAreaLeft.getText();
        String text2 = textAreaRight.getText();

        List<String> lines1 = List.of(text1.split("\\n"));
        List<String> lines2 = new ArrayList<>(List.of(text2.split("\\n")));

        for (int i = 0; i < lines1.size(); i++) { // Se baser sur la taille de lines1 pour la comparaison
            String line1 = lines1.get(i);
            String line2 = i < lines2.size() ? lines2.get(i) : "";

            Text textDisplay = new Text(line2 + " ");
            textDisplay.setStyle("-fx-fill: black;"); // Les lignes identiques sont en noir par défaut

            HBox lineBox = new HBox();
            lineBox.getChildren().add(textDisplay);

            if (!line1.equals(line2)) {
                if (line2.isEmpty()) {
                    textDisplay.setText(line1 + " "); // Affichage de la ligne manquante en vert
                    textDisplay.setStyle("-fx-fill: green;");
                } else {
                    textDisplay.setText(line2 + " "); // Affichage des différences en rouge
                    textDisplay.setStyle("-fx-fill: red;");
                }

                Button acceptButton = new Button("Accepter");
                Button rejectButton = new Button("Refuser");
                Button addCommentaryButton = new Button ("Ajouter Commentaire");
                Button showCommentButton = new Button("Afficher commentaire");

                acceptButton.setOnAction(e -> {
                    lineBox.getChildren().remove(acceptButton);
                    lineBox.getChildren().remove(rejectButton);
                    lineBox.getChildren().remove(addCommentaryButton);
                });

                int finalI = i;
                rejectButton.setOnAction(e -> {
                    if (finalI >= lines2.size()) {
                        while (lines2.size() <= finalI) { // Ajout de lignes vides si nécessaire
                            lines2.add("");
                        }
                    }
                    lines2.set(finalI, line1); // Synchronisation avec la ligne de gauche
                    textAreaRight.setText(String.join("\n", lines2));
                    textDisplay.setText(line1 + " "); // Mise à jour visuelle
                    textDisplay.setStyle("-fx-fill: black;"); // Rétablir la couleur noire après acceptation
                    lineBox.getChildren().remove(acceptButton);
                    lineBox.getChildren().remove(rejectButton);
                    lineBox.getChildren().remove(addCommentaryButton);
                });

                addCommentaryButton.setOnAction(e -> {
                    Stage commentStage = new Stage();
                    commentStage.initModality(Modality.APPLICATION_MODAL);
                    commentStage.setTitle("Ajouter un commentaire");

                    VBox commentBox = new VBox(10);
                    commentBox.setPadding(new Insets(10));

                    Label commentLabel = new Label("Commentaire :");
                    TextArea commentArea = new TextArea();
                    commentArea.setPrefRowCount(4); // Changer la taille de la zone de texte
                    Button confirmButton = new Button("Confirmer");

                    confirmButton.setOnAction(event -> {
                        String comment = commentArea.getText(); // traitement sur le commentaire

                        lineBox.getChildren().remove(addCommentaryButton);
                        lineBox.getChildren().add(showCommentButton);
                        commentStage.close();
                    });

                    Button cancelButton = new Button("Annuler");
                    cancelButton.setOnAction(event -> commentStage.close());

                    commentBox.getChildren().addAll(commentLabel, commentArea, confirmButton, cancelButton);

                    Scene commentScene = new Scene(commentBox, 300, 200); // Changer la taille de la fenêtre
                    commentStage.setScene(commentScene);
                    commentStage.showAndWait();
                });

                lineBox.getChildren().addAll(acceptButton, rejectButton, addCommentaryButton);
            }

            comparisonResult.getChildren().add(lineBox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


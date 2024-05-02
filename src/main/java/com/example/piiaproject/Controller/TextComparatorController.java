package com.example.piiaproject.Controller;

import com.example.projet_piia_nouvelle_version.Model.TextComparatorModel;
import com.example.projet_piia_nouvelle_version.View.TextComparatorView;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TextComparatorController {
    private TextComparatorModel model;
    private TextComparatorView view;

    public TextComparatorController(TextComparatorModel model, TextComparatorView view) {
        this.model = model;
        this.view = view;
        attachEventHandlers();
    }

    public void TextController(TextComparatorModel model, TextComparatorView view) {
        this.model = model;
        this.view = view;
        attachEventHandlers();
    }

    public void attachEventHandlers() {
        view.getLoadFileButtonLeft().setOnAction(e -> loadFile(view.getTextAreaLeft(), true));
        view.getLoadFileButtonRight().setOnAction(e -> loadFile(view.getTextAreaRight(), false));
        view.getCompareButton().setOnAction(e -> compareTexts());
    }

    private void loadFile(TextArea textArea, boolean isLeft) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        java.io.File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                List<String> lines = model.loadTextFromFile(Path.of(file.getPath()));
                textArea.setText(String.join("\n", lines));
                if (isLeft) {
                    view.setLeftTextLines(lines); // Supposant que TextView gère ces lignes
                } else {
                    view.setRightTextLines(lines);
                }
            } catch (IOException ex) {
                System.out.println("Erreur de lecture du fichier: " + ex.getMessage());
            }
        }
    }

    private void compareTexts() {
        view.getComparisonResult().getChildren().clear();
        String text1 = view.getTextAreaLeft().getText();
        String text2 = view.getTextAreaRight().getText();

        List<String> lines1 = List.of(text1.split("\\n"));
        List<String> lines2 = new ArrayList<>(List.of(text2.split("\\n")));
        List<String> comments = new ArrayList<>();

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

                int LastIndex = i;
                rejectButton.setOnAction(e -> {
                    if (LastIndex >= lines2.size()) {
                        while (lines2.size() <= LastIndex) { // Ajout de lignes vides si nécessaire
                            lines2.add("");
                        }
                    }
                    lines2.set(LastIndex, line1); // Synchronisation avec la ligne de gauche
                    view.getTextAreaRight().setText(String.join("\n", lines2));
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

                        // on s'assure que la liste comments a suffisamment d'éléments pour chaque ligne
                        while (comments.size() <= LastIndex) {
                            comments.add(""); // Ajouter des commentaires vides si nécessaire
                        }
                        comments.set(LastIndex, comment); // Ajouter ou mettre à jour le commentaire pour cette ligne

                        lineBox.getChildren().remove(addCommentaryButton);
                        lineBox.getChildren().add(showCommentButton);
                        commentStage.close();
                    });

                    showCommentButton.setOnAction(ev -> {
                        int IndexLine = LastIndex; // Récupérer l'index de la ligne
                        String comment = comments.get(IndexLine); // Récupérer le commentaire associé à cette ligne

                        Stage showcommentStage = new Stage();
                        showcommentStage.initModality(Modality.APPLICATION_MODAL);
                        showcommentStage.setTitle("Commentaire");

                        VBox showcommentBox = new VBox(10);
                        showcommentBox.setPadding(new Insets(10));

                        Label showcommentLabel = new Label("Commentaire :");
                        TextArea showcommentArea = new TextArea(comment);
                        showcommentArea.setPrefRowCount(4); // Changer la taille de la zone de texte
                        showcommentArea.setEditable(false); // Rendre le TextArea en lecture seule

                        Button closeButton = new Button("Fermer");
                        closeButton.setOnAction(event -> showcommentStage.close());

                        showcommentBox.getChildren().addAll(showcommentLabel, showcommentArea, closeButton);

                        Scene commentScene = new Scene(showcommentBox, 300, 200); // Changer la taille de la fenêtre
                        showcommentStage.setScene(commentScene);
                        showcommentStage.showAndWait();
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

            view.getComparisonResult().getChildren().add(lineBox);
        }
    }
}

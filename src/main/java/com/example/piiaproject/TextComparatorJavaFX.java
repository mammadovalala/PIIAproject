package com.example.projet_piia_nouvelle_version;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import javafx.geometry.Insets;
import java.util.List;
import java.util.ArrayList;

public class TextComparatorJavaFX extends Application {

    private TextArea textAreaLeft;
    private TextArea textAreaRight;
    private TextFlow comparisonResult;

    @Override
    public void start(Stage primaryStage) {
        textAreaLeft = new TextArea();
        textAreaRight = new TextArea();
        comparisonResult = new TextFlow();

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
        comparisonResult.getChildren().addAll(result);
    }

    private Text createText(String content, String style) {
        Text text = new Text(content);
        text.setStyle(style);
        return text;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

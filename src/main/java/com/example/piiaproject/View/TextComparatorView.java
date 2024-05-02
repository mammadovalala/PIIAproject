package com.example.piiaproject.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.projet_piia_nouvelle_version.Model.TextComparatorModel;
import com.example.projet_piia_nouvelle_version.Controller.TextComparatorController;

import java.util.List;

public class TextComparatorView extends Application {

    private final TextArea textAreaLeft = new TextArea();
    private final TextArea textAreaRight = new TextArea();
    private final VBox comparisonResult = new VBox(5);
    private final Button loadFileButtonLeft = new Button("Charger Fichier Gauche");
    private final Button loadFileButtonRight = new Button("Charger Fichier Droite");
    private final Button compareButton = new Button("Comparer Textes");

    public VBox initializeUI() {
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

        return new VBox(gridPane);
    }
    public Button getLoadFileButtonLeft() {
        return loadFileButtonLeft;
    }

    public Button getLoadFileButtonRight() {
        return loadFileButtonRight;
    }

    public TextArea getTextAreaLeft() {
        return textAreaLeft;
    }

    public TextArea getTextAreaRight() {
        return textAreaRight;
    }

    public Button getCompareButton() {
        return compareButton;
    }

    public VBox getComparisonResult(){
        return comparisonResult;
    }

    public void setLeftTextLines(List<String> lines) {
    }

    public void setRightTextLines(List<String> lines) {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextComparatorModel model = new TextComparatorModel();
        TextComparatorView view = new TextComparatorView();
        new TextComparatorController(model, view);

        Scene scene = new Scene(view.initializeUI(), 1000, 700);
        primaryStage.setTitle("Text Comparator - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

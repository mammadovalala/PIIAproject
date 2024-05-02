package com.example.piiaproject.Model;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileModel {
    private File fileRef;
    private File fileModif;
    private TextFlow comparisonResult;

    private List<File> recentFiles = new ArrayList<>();
    public FileModel(File fileRef, File fileModif) {
        this.fileRef = fileRef;
        this.fileModif = fileModif;
    }

    public File getFileRef() {
        return fileRef;
    }
    public List<File> getRecentFiles() {
        return recentFiles;
    }
    public void addRecentFile(File file) {
        recentFiles.add(file);
    }
    public void setFileRef(File fileRef) {
        this.fileRef = fileRef;
    }

    public File getFileModif() {
        return fileModif;
    }

    public void setFileModif(File fileModif) {
        this.fileModif = fileModif;
    }

    private String readFileContents(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /*private Text createText(String content, String style) {
        Text text = new Text(content);
        text.setStyle(style);
        return text;
    }

    private void compareTexts() throws IOException {
        comparisonResult.getChildren().clear();
        String text1 = readFileContents(fileRef.getAbsolutePath());
        String text2 = readFileContents(fileModif.getAbsolutePath());

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
    }*/


}
package com.example.piiaproject.Model;

import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class File {
    private ArrayList<String> lines = new ArrayList<String>();

    public File(String filePath) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String text;
            while((text = reader.readLine()) != null){
                lines.add(text);
            }
        }
    }

    public ArrayList<String> getLines(){
        return lines;
    }

    public boolean equals(File f){
        return lines.equals(f.lines);
    }

    public String toString(){
        String s = "";
        for (String line: this.lines) {
            s += line + "\n";
        }
        return s;
    }

    /*public static TextFlow compareTexts(String text1, String text2) {
        TextFlow textFlow = new TextFlow();

            for (int i = 0; i < Math.min(text1.length(), text2.length()); i++) {
                char char1 = text1.charAt(i);
                char char2 = text2.charAt(i);
                Text textNode = new Text(String.valueOf(char1));
                if (char1 != char2) {
                    textNode.setFill(Color.RED);
                    textNode.setUnderline(true);
                }
                textFlow.getChildren().add(textNode);
            }

        return textFlow;
    }*/

    public static void main(String[] args) throws IOException {



        /*TextFlow comparedText = compareTexts(f1.toString(), f2.toString());*/

    }
}

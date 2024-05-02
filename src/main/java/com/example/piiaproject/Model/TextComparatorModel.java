package com.example.piiaproject.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextComparatorModel {
    public List<String> loadTextFromFile(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        return List.of(content.split("\\n"));
    }

    // Autres méthodes pour manipuler les données
}
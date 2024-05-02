package com.example.piiaproject.Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileModel {
    private File fileRef;
    private File fileModif;

    public FileModel(File fileRef, File fileModif) {
        this.fileRef = fileRef;
        this.fileModif = fileModif;
    }

    public File getFileRef() {
        return fileRef;
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


}
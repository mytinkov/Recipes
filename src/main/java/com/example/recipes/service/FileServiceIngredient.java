package com.example.recipes.service;

import com.example.recipes.exception.ExceptionWithOperationFile;

import java.io.File;

public interface FileServiceIngredient {
    boolean saveToFile(String json) throws ExceptionWithOperationFile;

    String readFromFile() throws ExceptionWithOperationFile;

    File getDataFile();

    boolean cleanDataFile();
}

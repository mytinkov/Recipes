package com.example.recipes.service;

import com.example.recipes.exception.ExceptionWithOperationFile;

import java.io.File;
import java.nio.file.Path;

public interface FileServiceRecipe {
    boolean saveToFile(String json) throws ExceptionWithOperationFile;

    String readFromFile() throws ExceptionWithOperationFile;

    File getDataFile();

    boolean cleanDataFile();

    Path createTempFile(String suffix);


}

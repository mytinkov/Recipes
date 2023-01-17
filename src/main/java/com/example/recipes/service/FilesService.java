package com.example.recipes.service;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

}

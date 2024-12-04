package com.example.recipes.service.impl;

import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.service.FileServiceRecipe;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceRecipeImpl implements FileServiceRecipe {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${namer.of.data.file}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json) throws ExceptionWithOperationFile {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json); //Записываем строку в json
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }

    @Override
    public String readFromFile() throws ExceptionWithOperationFile {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName)); //Считывает все содержимое файла в строку, декодируя байты в символы
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
        }
    }

    @Override
    public boolean cleanDataFile() {

        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path); //Удаляем если есть такой файл/если его нет, ничего не делает
            Files.createFile(path); //создает чистый файл
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping(value = "/export/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Сохранение рецепта в файл",
            description = "Сохранение рецепта в файл"
    )
    public ResponseEntity<InputStreamResource> downloadDataFileRecipe() throws FileNotFoundException {
        File dataFile = getDataFile();
        if (dataFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((dataFile)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(dataFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"RecipeData.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
        //Мы возвращаем файл не касаясь его внутренего содержания, класс File работает только с метаинформацией файла
    }

    @Override
    public Path createTempFile(String suffix) {  /*Метод создает временные файлы. Возвращаем путь к нашему файлу*/
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix); //префик и суфикс формируют название
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
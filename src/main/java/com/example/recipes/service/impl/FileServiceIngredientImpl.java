package com.example.recipes.service.impl;

import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.service.FileServiceIngredient;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceIngredientImpl implements FileServiceIngredient {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${namei.of.data.file}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json) throws ExceptionWithOperationFile {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }

    @Override
    public String readFromFile() throws ExceptionWithOperationFile {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
        }
    }

    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }

    @Override
    public boolean cleanDataFile() {

        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Operation(
            summary = "Загрузка рецепта из файла",
            description = "Загрузка рецепта из файла"
    )

    @PostMapping(value = "/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientForFile(@RequestParam MultipartFile file) throws ExceptionWithOperationFile {
        cleanDataFile();
        File dataFile = getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ExceptionWithOperationFile("Файл не может быть прочитан");
    }
}

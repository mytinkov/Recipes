package com.example.recipes.controller;

import com.example.recipes.service.FileServiceIngredient;
import com.example.recipes.service.FileServiceRecipe;
import com.example.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@Tag(name = ("Работа с файлами"), description = "операции с файлами")

public class FilesController {

    private final FileServiceRecipe fileServiceRecipe;
    private final FileServiceIngredient fileServiceIngredient;
    private RecipeService recipeService;

    public FilesController(FileServiceRecipe fileServiceRecipe, FileServiceIngredient fileServiceIngredient, RecipeService recipeService) {
        this.fileServiceRecipe = fileServiceRecipe;
        this.fileServiceIngredient = fileServiceIngredient;
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Сохранение всех ингредиентов в файл json",
            description = "Сохранние в формате json"
    )
    @GetMapping(value = "/export/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadIngredientDataFile() throws FileNotFoundException {
        File file = fileServiceIngredient.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"ingredient.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Сохранение всех рецептов в файл json",
            description = "Сохранние в формате json"
    )
    @GetMapping(value = "/export/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadRecipeDataFile() throws FileNotFoundException {
        File file = fileServiceRecipe.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"recipe.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }



    @Operation(
            summary = "Загрузка ингредиента из файла",
            description = "Загрузка ингредиента из файла"
    )
    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientDataFile(@RequestParam MultipartFile file) {
        fileServiceIngredient.cleanDataFile();
        File dataFileIngredient = fileServiceIngredient.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFileIngredient)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(
            summary = "Загрузка Рецепта из файла",
            description = "Загрузка Рецепта из файла"
    )
    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeDataFile(@RequestParam MultipartFile file) {
        fileServiceRecipe.cleanDataFile();
        File dataFileRecipe = fileServiceRecipe.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFileRecipe)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @Operation(
            summary = "Сохранить все рецепты",
            description = "в формате txt"
    )
    @GetMapping("/file")
    public ResponseEntity<InputStreamResource> getRecipeTxt() {
        try {
            Path path = recipeService.createRecipeFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesFile.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

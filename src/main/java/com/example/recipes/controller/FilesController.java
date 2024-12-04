package com.example.recipes.controller;

import com.example.recipes.service.FileServiceIngredient;
import com.example.recipes.service.FileServiceRecipe;
import com.example.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
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

    private final FileServiceRecipe fileServiceRecipe; //инжектим для работы с методами
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
        File file = fileServiceIngredient.getDataFile(); //Получаем информацию о файле
        if (file.exists()) { /*проверяем, существует ли файл*/
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file))); //Создаем входной поток из файла
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON) //указываем что конкретно мы передаем. contentType - заголовок запроса URL
                    //APPLICATION_OCTET_STREAM - говорит, что это поток байт
                    .contentLength(file.length()) //Чтобы браузер понял, сколько все байт должно было быть получено. contentLength - это заголовок URL
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"ingredient.json\"") // attachment - говорит о том что его надо обязательно скачать, также указываем имя файла при скачивании
                    .body(resource); //передаем ресурс в тело ответа, чтобы пользователь мог его скачать (читаем поток в браузере)
        } else {
            return ResponseEntity.noContent().build(); //noContent - 204 статус (все хорошо но нет содержимого)
        }
    }

    @Operation(
            summary = "Сохранение всех рецептов в файл json",
            description = "Сохранние в формате json"
    )
    @GetMapping(value = "/export/recipe", produces = MediaType.APPLICATION_JSON_VALUE) //APPLICATION_JSON.. говорим что всегда будет генерироваться json
    public ResponseEntity<InputStreamResource> downloadRecipeDataFile() throws FileNotFoundException {
        File file = fileServiceRecipe.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"recipe.json\"")
                    .body(resource); //читаем поток
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
    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // consumes - мы указываем, что принимаем запросы только с типом Multipart...
    public ResponseEntity<Void> uploadRecipeDataFile(@RequestParam MultipartFile file) { //в @RP получаем файл
        fileServiceRecipe.cleanDataFile();  //удаляем дата файл и создаем пустой новый
        File dataFileRecipe = fileServiceRecipe.getDataFile(); //берем про него информацию

        try (FileOutputStream fos = new FileOutputStream(dataFileRecipe)) {   // Открываем исходящий поток. try со стримами сам закрывает поток
            IOUtils.copy(file.getInputStream(), fos); //Берем входящий поток из запроса (file.getInputStream()), копируем в исходящий.
            return ResponseEntity.ok().build(); //Если все ок отправляем статус 200
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //Ошибка 500
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
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile())); //Превращаем наш путь в файл
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesFile.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace(); //печатаем в консоль
            return ResponseEntity.internalServerError().build(); //отправляем ошибку на клиента
        }
    }
}


    /*Пример написания import без использования библиотеки Apache Commons IO (mvnrepository.com)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
    fileService.cleanDataFile();
    File dataFile = fileService.getDataFile;

    try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
    FileOutputStream fos = new FileOutputStream(dataFile);
    BufferedOutputStream bos = new BufferedOutputStream(fos)) {
        byte[] buffer = new byte[1014];
        while (bis.read(buffer) > 0) {
        bos.write(buffer);
        }
    }
     */
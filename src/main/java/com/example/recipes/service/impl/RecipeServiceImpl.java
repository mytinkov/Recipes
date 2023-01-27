package com.example.recipes.service.impl;

import com.example.recipes.exception.ExceptionWithCheckingRecipes;
import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private FileServiceRecipeImpl fileServiceRecipe; //инжектим для доступа к методам, добавляем в конструктор
    private Map<String, Recipe> recipes = new HashMap<String, Recipe>();
    private Long counter = 0L;


    public RecipeServiceImpl(FileServiceRecipeImpl fileServiceRecipe) {
        this.fileServiceRecipe = fileServiceRecipe;
    }

    @PostConstruct //При запуске программы автоматически читаем файл из которого создается объект
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Collection<Recipe> getAllRecipe() {
        return recipes.values();
    }

    @Override
    public Recipe addNewRecipe(Recipe recipe) throws ExceptionWithCheckingRecipes, ExceptionWithOperationFile {
        if (recipes.containsKey(counter)) {
            throw new ExceptionWithCheckingRecipes("Такой рецепт уже есть");
        } else {
            recipes.put(String.valueOf(this.counter++), recipe); //Делаем увеличение счетчка(ключа в карте) при добавлении нового рецепта + добавлем его в виде строки
            saveToFile(); //Делаем сохранение в файл после добавления рецепта
        }
        return recipe;
    }

    @Override
    public Recipe editRecipe(String id, Recipe recipe) throws ExceptionWithCheckingRecipes, ExceptionWithOperationFile {
        Recipe serviceRecipe = recipes.get(id);
        if (serviceRecipe == null) {
            throw new ExceptionWithCheckingRecipes("Нет такого рецепта");
        }
        serviceRecipe.setName(recipe.getName());
        serviceRecipe.setIngredients(recipe.getIngredients());
        serviceRecipe.setTimeCooking(recipe.getTimeCooking());
        serviceRecipe.setStep(recipe.getStep());
        saveToFile();
        return serviceRecipe;
    }

    @Override
    public Recipe removeRecipe(String id) {
        return recipes.remove(id);
    }


    public void saveToFile() throws ExceptionWithOperationFile {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes); //Создаем строку из объекта
            fileServiceRecipe.saveToFile(json); //Передаем строку в метод сохранения сервиса работы с файлами
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }


    public void readFromFile() throws ExceptionWithOperationFile {
        try {
            String json = fileServiceRecipe.readFromFile(); //создаем строку из прочитанного файла (в сервисе по работе с файлами)
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {}); //Пытаемся собрать объект определенного типа из прочитанной строки
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
        }


    }

    @Override
    public Path createRecipeFile() throws IOException {
        Path path = fileServiceRecipe.createTempFile("recipes"); //Создаем временный файл с новым названием, и передаем путь
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                /*Используем Reader т.к. работаем с текстом.
                * Открываем исходящий поток по указанному пути для записи во временный файл.
                * Если оставить только path, то при каждой записи файл будет перезаписываться.
                * Поэтому добавляем опцию добавления в файл StandardOpenOption.APPEND*/
                writer.append(("Название рецепта: " + recipe.getName() + '\n' + '\n' +
                        "Время приготовления: " + recipe.getTimeCooking() + '\n' + '\n' +
                        "Ингредиенты: " + recipe.getIngredients() + '\n' + '\n' +
                        "Инструкция приготовления: " + '\n' + '\n' + recipe.getStep() + '\n'));
            }
        }
        return path;
    }

}
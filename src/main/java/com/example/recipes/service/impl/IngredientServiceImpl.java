package com.example.recipes.service.impl;

import com.example.recipes.exception.ExceptionWithCheckingIngredients;
import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.model.Ingredient;
import com.example.recipes.service.FileServiceIngredient;
import com.example.recipes.service.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class IngredientServiceImpl implements IngredientService {
    private FileServiceIngredient fileServiceIngredient;

    private Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

    public IngredientServiceImpl(FileServiceIngredient fileServiceIngredients) {
        this.fileServiceIngredient = fileServiceIngredients;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Ingredient> getAllIngredient() {
        return ingredients.values();
    }

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) throws ExceptionWithCheckingIngredients, ExceptionWithOperationFile {
        if (ingredients.containsKey(ingredient.getTitle())) {
            throw new ExceptionWithCheckingIngredients("такой ингридиент уже есть");
        } else {
            ingredients.put(ingredient.getTitle(), ingredient);
            saveToFile();
        }
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(String id, Ingredient ingredient) throws ExceptionWithCheckingIngredients, ExceptionWithOperationFile {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {

            throw new ExceptionWithCheckingIngredients("Нет такого ингредиента");
        }
        serviceIngredient.setTitle(ingredient.getTitle());
        serviceIngredient.setTotalIngredients(ingredient.getTotalIngredients());
        saveToFile();
        return serviceIngredient;
    }

    @Override
    public Ingredient removeIngredientById(String id) {
        return ingredients.remove(id);
    }

    public void saveToFile() throws ExceptionWithOperationFile {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileServiceIngredient.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }


    public void readFromFile() throws ExceptionWithOperationFile {
        try {
            String json = fileServiceIngredient.readFromFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
        }
    }
}

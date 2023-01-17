package com.example.recipes.service.impl;

import com.example.recipes.model.Ingredient;
import com.example.recipes.service.FilesService;
import com.example.recipes.service.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Month;
import java.util.*;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FilesService filesService;
    private HashMap<Integer, Ingredient> ingredients = new HashMap<>();

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    public void unit() {
        readFromFile();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient.getId())) {
            throw new RuntimeException("This ingredient already exists");
        } else {
            ingredients.put(ingredient.getId(), ingredient);
            //saveToFile();
        }
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(int id, Ingredient ingredient) {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {
            throw new RuntimeException("No such ingredient");
        }
        serviceIngredient.setIngredientName(ingredient.getIngredientName());
        serviceIngredient.setQuantityOfIngredients(ingredient.getQuantityOfIngredients());
        serviceIngredient.setUnit(ingredient.getUnit());
        //saveToFile();
        return serviceIngredient;
    }

    @Override
    public Ingredient deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.remove(id);
        } else {
            throw new RuntimeException("No such ingredient");
        }
    }

    @Override
    public Ingredient getIngredient(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new RuntimeException("No such ingredient");
        }
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients); //подготовка строки через jackson - обработка объекта в json, закинули карту transactions
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            ingredients = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}

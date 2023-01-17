package com.example.recipes.service.impl;

import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.FilesService;
import com.example.recipes.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    //private final FilesService filesService;
    private static HashMap<Integer, Recipe> recipes = new HashMap<>();

    /*public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }*/

    /*@PostConstruct
    public void unit() {
        readFromFile();
    }*/

    @Override
    public Recipe getRecipe(int id) {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        } else {
            throw new RuntimeException("No such recipe");
        }
    }

    @Override
    public Collection<Recipe> getAllRecipes() {
        return recipes.values();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipes.containsKey(recipe.getId())) {
            throw new RuntimeException("This recipe already exists");
        } else {
            recipes.put(recipe.getId(), recipe);
        }
        return recipe;
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            return recipes.put(id, recipe);
        } else {
            throw new RuntimeException("No such recipe");
        }
    }

    @Override
    public Recipe deleteRecipe(int id) {
        if (recipes.containsKey(id)) {
            return recipes.remove(id);
        }
        throw new RuntimeException("No such recipe");
    }

    /*private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes); //подготовка строки через jackson - обработка объекта в json, закинули карту transactions
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            recipes = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/


    }

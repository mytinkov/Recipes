package com.example.recipes.service;

import com.example.recipes.exception.ExceptionWithCheckingIngredients;
import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.model.Ingredient;

import java.util.Collection;

public interface IngredientService {
    Collection<Ingredient> getAllIngredient();

    Ingredient addNewIngredient(Ingredient ingredient) throws ExceptionWithCheckingIngredients, ExceptionWithOperationFile;

    Ingredient updateIngredient(String id, Ingredient ingredient) throws ExceptionWithCheckingIngredients, ExceptionWithOperationFile;

    Ingredient removeIngredientById(String id);

    void saveToFile() throws ExceptionWithOperationFile;

    void readFromFile() throws ExceptionWithOperationFile;
}

package com.example.recipes.service;

import com.example.recipes.exception.ExceptionWithCheckingRecipes;
import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface RecipeService {
    void readFromFile() throws ExceptionWithOperationFile;

    void saveToFile() throws ExceptionWithOperationFile;

    Collection<Recipe> getAllRecipe();

    Recipe addNewRecipe(Recipe recipe) throws ExceptionWithCheckingRecipes, ExceptionWithOperationFile;

    Recipe editRecipe(String id, Recipe recipe) throws ExceptionWithCheckingRecipes, ExceptionWithOperationFile;

    Recipe removeRecipe(String id);

    Path createRecipeFile() throws IOException;
}

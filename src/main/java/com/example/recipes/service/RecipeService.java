package com.example.recipes.service;

import com.example.recipes.model.Recipe;

import java.util.Collection;

public interface RecipeService {
    Recipe getRecipe(int id);

    Collection<Recipe> getAllRecipes();

    Recipe addRecipe(Recipe recipe);

    Recipe updateRecipe(int id, Recipe recipe);

    Recipe deleteRecipe(int id);
}

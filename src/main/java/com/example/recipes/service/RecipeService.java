package com.example.recipes.service;

import com.example.recipes.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class RecipeService {
    private static Map<Integer, Recipe> recipes = new HashMap<>();

    public Recipe getRecipe(int id) {
        if (recipes.containsKey(id)){
            return recipes.get(id);
        } else {
            throw new RuntimeException("No such recipe");
        }
    }

    public Recipe addRecipe(Recipe recipe) {
        if (recipes.containsKey(recipe.getId())) {
            throw new RuntimeException("This recipe already exists");
        } else {
            recipes.put(recipe.getId(), recipe);
        }
        return recipe;
    }

    public Collection<Recipe> getAllRecipes() {
        return recipes.values();
    }
}

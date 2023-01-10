package com.example.recipes.service.impl;

import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class RecipeServiceImpl implements RecipeService{
    private static Map<Integer, Recipe> recipes = new HashMap<>();

    @Override
    public Recipe getRecipe(int id) {
        if (recipes.containsKey(id)){
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
}

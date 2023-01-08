package com.example.recipes.service;

import com.example.recipes.model.Ingredient;

import java.util.Collection;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient updateIngredient(int id, Ingredient ingredient);

    Ingredient deleteIngredient(int id);

    Ingredient getIngredient(int id);

    Collection<Ingredient> getAllIngredients();
}

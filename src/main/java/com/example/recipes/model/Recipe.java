package com.example.recipes.model;

import lombok.*;

import java.util.*;

@Data
public class Recipe {
    private String recipeName;
    private int timeForPreparing;
    private final Set<Ingredient> ingredients;
    private List<String> cookingSteps = new LinkedList<>();
    private int id;
    private static int counter;


    public Recipe(String recipeName, int timeForPreparing, Set<Ingredient> ingredients, List<String>cookingSteps) {
        this.recipeName = recipeName;
        this.timeForPreparing = timeForPreparing;
        this.ingredients = ingredients;
        this.cookingSteps.add(String.valueOf(cookingSteps));
        this.id = counter++;
    }
}

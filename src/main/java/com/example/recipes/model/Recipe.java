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
        setTimeForPreparing(timeForPreparing);
        this.ingredients = ingredients;
        this.cookingSteps.add(String.valueOf(cookingSteps));
        this.id = counter++;
    }

    public void setTimeForPreparing(int timeForPreparing) {
        if (timeForPreparing < 0) {
            throw new IllegalArgumentException("Don't allow negative argument");
        } else {
            this.timeForPreparing = timeForPreparing;
        }
    }
}

package com.example.recipes.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@EqualsAndHashCode
@ToString
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

    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getTimeForPreparing() {
        return timeForPreparing;
    }

    public void setTimeForPreparing(int timeForPreparing) {
        if (timeForPreparing < 0) {
            throw new IllegalArgumentException("Don't allow negative argument");
        } else {
            this.timeForPreparing = timeForPreparing;
        }
    }

    public Set<Ingredient> getIngredient() {
        return ingredients;
    }



    public List<String> getCookingSteps() {
        return cookingSteps;
    }

    public void setCookingSteps(List<String> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }
}

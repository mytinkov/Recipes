package com.example.recipes.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@EqualsAndHashCode
@ToString
public class Recipe {
    private String recipeName;
    private int timeForPreparing;

    private Ingredient ingredient;
    private List<String> cookingSteps = new LinkedList<>();

    private int id = 0;

    public Recipe(String recipeName, int timeForPreparing, Ingredient ingredient, List<String>cookingSteps, int id) {
        this.recipeName = recipeName;
        setTimeForPreparing(timeForPreparing);
        this.ingredient = ingredient;
        this.cookingSteps.add(String.valueOf(cookingSteps));
        this.id = id;
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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public List<String> getCookingSteps() {
        return cookingSteps;
    }

    public void setCookingSteps(List<String> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }


}

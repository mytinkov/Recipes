package com.example.recipes.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Ingredient {
    private String ingredientName;
    private int quantityOfIngredients;
    private String unit;
    private int id;
    private static int counter;

    public Ingredient(String ingredientName, int quantityOfIngredients, String unit) {
        this.ingredientName = ingredientName;
        setQuantityOfIngredients(quantityOfIngredients);
        this.unit = unit;
        this.id = counter++;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getQuantityOfIngredients() {
        return quantityOfIngredients;
    }

    public void setQuantityOfIngredients(int quantityOfIngredients) {
        if (quantityOfIngredients < 0) {
            throw new IllegalArgumentException("Don't allow negative argument");
        } else {
            this.quantityOfIngredients = quantityOfIngredients;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

}

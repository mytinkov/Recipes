package com.example.recipes.model;

import lombok.Data;

@Data
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

    public void setQuantityOfIngredients(int quantityOfIngredients) {
        if (quantityOfIngredients < 0) {
            throw new IllegalArgumentException("Don't allow negative argument");
        } else {
            this.quantityOfIngredients = quantityOfIngredients;
        }
    }
}

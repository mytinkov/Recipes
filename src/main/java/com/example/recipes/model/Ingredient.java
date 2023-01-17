package com.example.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredient {
    private String ingredientName;
    private int quantityOfIngredients;
    private String unit;
    private int id;
    private static int counter;

    public Ingredient(String ingredientName, int quantityOfIngredients, String unit) {
        this.ingredientName = ingredientName;
        this.quantityOfIngredients = quantityOfIngredients;
        this.unit = unit;
        this.id = counter++;
    }
}

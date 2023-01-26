package com.example.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Ingredient {
    public String title;
    private int totalIngredients;
    private String unit;

    @Override
    public String toString() {
        return title + ": " + totalIngredients + " " + unit;
    }
}

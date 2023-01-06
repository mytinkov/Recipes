package com.example.recipes.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Ingredient {
    private String INGREDIENT_NAME;
    private int quantityOfIngredients;
    private String unit;
    private int id = 0;

    public Ingredient(String INGREDIENT_NAME, int quantityOfIngredients, String unit, int id) {
        this.INGREDIENT_NAME = INGREDIENT_NAME;
        setQuantityOfIngredients(quantityOfIngredients);
        this.unit = unit;
        this.id = id;
    }

    public String getINGREDIENT_NAME() {
        return INGREDIENT_NAME;
    }

    public void setINGREDIENT_NAME(String INGREDIENT_NAME) {
        this.INGREDIENT_NAME = INGREDIENT_NAME;
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

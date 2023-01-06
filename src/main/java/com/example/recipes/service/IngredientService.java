package com.example.recipes.service;

import com.example.recipes.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientService {
    private final Map<Integer, Ingredient> ingredients = new HashMap<>();

    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient.getId())) {
            throw new RuntimeException("This ingredient already exists");
        } else {
            ingredients.put(ingredient.getId(), ingredient);
        }
        return ingredient;
    }

    public Ingredient updateIngredient(int id, Ingredient ingredient) {
        Ingredient serviceIngredient = ingredients.get(id);
        if (serviceIngredient == null) {
            throw new RuntimeException("No such ingredient");
        }
        serviceIngredient.setIngredientName(ingredient.getIngredientName());
        serviceIngredient.setQuantityOfIngredients(ingredient.getQuantityOfIngredients());
        serviceIngredient.setUnit(ingredient.getUnit());
        return serviceIngredient;
    }

    public Ingredient deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.remove(id);
        } else {
            throw new RuntimeException("No such ingredient");
        }
    }

    public Ingredient getIngredient(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new RuntimeException("No such ingredient");
        }
    }

    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

}

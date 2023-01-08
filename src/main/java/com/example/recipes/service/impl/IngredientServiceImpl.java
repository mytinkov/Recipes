package com.example.recipes.service.impl;

import com.example.recipes.model.Ingredient;
import com.example.recipes.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<Integer, Ingredient> ingredients = new HashMap<>();

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient.getId())) {
            throw new RuntimeException("This ingredient already exists");
        } else {
            ingredients.put(ingredient.getId(), ingredient);
        }
        return ingredient;
    }

    @Override
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

    @Override
    public Ingredient deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.remove(id);
        } else {
            throw new RuntimeException("No such ingredient");
        }
    }

    @Override
    public Ingredient getIngredient(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new RuntimeException("No such ingredient");
        }
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

}

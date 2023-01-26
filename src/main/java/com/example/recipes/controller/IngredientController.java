package com.example.recipes.controller;

import com.example.recipes.exception.ExceptionWithCheckingIngredients;
import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.model.Ingredient;
import com.example.recipes.service.impl.IngredientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredient")
@Tag(name = ("Ингридиенты"), description = "оперции с ингридиентами")
public class IngredientController {
    private final IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @Operation(
            summary = "Получение списка ингридиентов",
            description = "получение списка ингридиентов"
    )
    public Collection<Ingredient> getAll() {
        return this.ingredientService.getAllIngredient();
    }

    @PostMapping
    @Operation(
            summary = "Добавление ингридиента",
            description = "добавление ингридиента"
    )
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) throws ExceptionWithCheckingIngredients, ExceptionWithOperationFile {
        return ingredientService.addNewIngredient(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление ингридиента",
            description = "обновление ингридиента по ID"
    )
    public Ingredient updateIngredient(@PathVariable("id") String id, @RequestBody Ingredient ingredient) throws ExceptionWithCheckingIngredients, ExceptionWithOperationFile {
        return ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингридиента",
            description = "удаление ингридиента по ID"
    )
    public Ingredient removeIngredientById(@PathVariable("id") String id) {
        return ingredientService.removeIngredientById(id);
    }
}

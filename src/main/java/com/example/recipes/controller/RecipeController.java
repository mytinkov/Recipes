package com.example.recipes.controller;

import com.example.recipes.exception.ExceptionWithCheckingRecipes;
import com.example.recipes.exception.ExceptionWithOperationFile;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.impl.RecipeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/recipe")
@Tag(name = ("Рецепт"), description = "операции с рецептами")
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(
            summary = "Получение списка рецептов",
            description = "получение списка рецептов"
    )
    public Collection<Recipe> getAllRecipe() {
        return recipeService.getAllRecipe();
    }

    @PostMapping
    @Operation(
            summary = "Добавление нового рецепта",
            description = "добавление нового рецепта"
    )
    public Recipe addNewRecipe(@RequestBody Recipe recipe) throws ExceptionWithCheckingRecipes, ExceptionWithOperationFile {
        return recipeService.addNewRecipe(recipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение рецепта",
            description = "изменение рецепта по ID"
    )
    public Recipe editRecipe(@PathVariable("id") String id, Recipe recipe) throws ExceptionWithCheckingRecipes, ExceptionWithOperationFile {
        return recipeService.editRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта",
            description = "удаление рецепта по ID"
    )
    public Recipe removeRecipe(@PathVariable("id") String id) {
        return recipeService.removeRecipe(id);
    }
}
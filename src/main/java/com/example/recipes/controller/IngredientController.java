package com.example.recipes.controller;

import com.example.recipes.model.Ingredient;
import com.example.recipes.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Игредиенты", description = "CRUD операции и другие эндпоинты для работы с игредиентами")

public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping
    @Operation(
            summary = "Отображение всех игредиентов",
            description = "Работает без параметров"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Collection<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(this.ingredientService.getAllIngredients());
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск игредиента",
            description = "Ввод id обязателен"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.ingredientService.getIngredient(id));
    }


    @PostMapping
    @Operation(
            summary = "Добавление новых ингредиентов",
            description = "Данные добавляются в формате json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(this.ingredientService.addIngredient(ingredient));
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение ингредентов",
            description = "Поиск осуществляется по id игредиента, информация обновляется в формате json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
            this.ingredientService.updateIngredient(id, ingredient);
            return ResponseEntity.ok(this.ingredientService.updateIngredient(id, ingredient));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Удаление осуществляется по id игредиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> deleteIngredient(@PathVariable("id") int id) {
        this.ingredientService.deleteIngredient(id);
        return ResponseEntity.ok("Игредиент под номером " + id + " удален!");
    }


    // Прошлая реализация
    /*    @GetMapping
    public Collection<Ingredient> getAllIngredients() {
        return this.ingredientService.getAllIngredients();


    @GetMapping("/{id}")
    public Ingredient getIngredientById(@PathVariable("id") int id) {
        return this.ingredientService.getIngredient(id);
    }

    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return this.ingredientService.addIngredient(ingredient);


    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        return this.ingredientService.updateIngredient(id, ingredient);


    @DeleteMapping("/{id}")
    public Ingredient deleteIngredient(@PathVariable("id") int id) {
        return this.ingredientService.deleteIngredient(id);
    }*/
}

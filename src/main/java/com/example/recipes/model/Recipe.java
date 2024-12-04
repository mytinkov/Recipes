package com.example.recipes.model;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Recipe {
    private String name;
    private int timeCooking;
    private List<Ingredient> ingredients;
    private List<String> step;
}

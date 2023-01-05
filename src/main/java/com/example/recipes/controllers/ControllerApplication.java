package com.example.recipes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerApplication {
    @GetMapping
    public String Hello() {
        return "Привет, это приложение с рецептами!";
    }

    @GetMapping("/info")
    public String info(@RequestParam String name, String nameProject, String date, String describe) {
        return "Имя ученика: " + name +
                ". Название проекта: " + nameProject +
                ". Дата создания проекта: " + date +
                ". Описание проекта: " + describe + ".";
    }
}

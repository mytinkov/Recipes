package com.example.recipes.controller;

import com.example.recipes.record.InfoRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Tag(name = "Стартовая страница", description = "Общая информация по проекту")

public class InfoController {

    @GetMapping
    @Operation(
            summary = "Запуск приложения",
            description = "Если запустилось, значит все ОК"
    )
    public String Hello() {
        return "Привет, это приложение с рецептами!";
    }

    @GetMapping("/info")
    @Operation(
            summary = "Информация по проекту",
            description = "Имя разработчика, название проекта, дата начала проекта, описание проекта"
    )
    public InfoRecord info() {
        return new InfoRecord("Maxim T", "Recipe Application", LocalDate.of(2022, 12, 31), "Description test");
    }
}

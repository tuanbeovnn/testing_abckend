package com.myblogbackend.blog.fakers;

import com.github.javafaker.Faker;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class GenerateCategoriesCommand implements CommandLineRunner {

    private final CategoryService categoryService;
    private final Environment environment;

    public GenerateCategoriesCommand(CategoryService categoryService, Environment environment) {
        this.categoryService = categoryService;
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean shouldGenerate = environment.getProperty("app.generate-categories", Boolean.class, false);
        if (!shouldGenerate) {
            return;
        }
        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            CategoryRequest categoryRequest = CategoryRequest.builder()
                    .name(faker.commerce().productName())
                    .build();
            categoryService.createCategory(categoryRequest);
        }
    }
}
package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.controllers.route.CategoryRoutes;
import com.myblogbackend.blog.controllers.route.CommonRoutes;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonRoutes.BASE_API + CommonRoutes.VERSION + CategoryRoutes.BASE_URL)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        var categoryList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(final @PathVariable(value = "id") Long id) {
        var category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(final CategoryRequest categoryRequest) {
        var categoryResponse = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(final CategoryRequest categoryRequest, final @PathVariable(value = "id") Long id) {
        var category = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(category);
    }
}

package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.exception.ResourceNotFoundException;
import com.myblogbackend.blog.mapper.CategoryMapper;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import com.myblogbackend.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        var categoryList = categoryRepository.findAll();
        return categoryMapper.toListCategoryResponse(categoryList);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        var category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        var category = categoryMapper.toCategoryEntityFromCategoryRequest(categoryRequest);
        var createdCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(createdCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        var category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        category.setName(categoryRequest.getName());
        var updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(updatedCategory);
    }
}

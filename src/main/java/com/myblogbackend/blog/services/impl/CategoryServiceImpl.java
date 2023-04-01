package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.exception.ResourceNotFoundException;
import com.myblogbackend.blog.mapper.CategoryMapper;
import com.myblogbackend.blog.pagination.OffsetPageRequest;
import com.myblogbackend.blog.pagination.PaginationPage;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import com.myblogbackend.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public PaginationPage<CategoryResponse> getAllCategories(final Integer offset, final Integer limited) {
        var pageable = new OffsetPageRequest(offset, limited);
        var categoryList = categoryRepository.findAll(pageable);
        var categoryResponse = categoryList.getContent()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
        return new PaginationPage<CategoryResponse>()
                .setRecords(categoryResponse)
                .setLimit(categoryList.getSize())
                .setTotalRecords(categoryList.getTotalElements())
                .setOffset(categoryList.getNumber());
    }

    @Override
    public CategoryResponse getCategoryById(final UUID id) {
        var category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(final CategoryRequest categoryRequest) {
        var category = categoryMapper.toCategoryEntityFromCategoryRequest(categoryRequest);
        var createdCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(createdCategory);
    }

    @Override
    public CategoryResponse updateCategory(final UUID id, final CategoryRequest categoryRequest) {
        var category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        category.setName(categoryRequest.getName());
        var updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(updatedCategory);
    }
}

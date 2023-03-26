package com.myblogbackend.blog.mapper;

import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toCategoryEntity(CategoryResponse categoryResponse);

    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

    List<CategoryResponse> toListCategoryResponse(List<CategoryEntity> categoryEntityList);

    CategoryEntity toCategoryEntityFromCategoryRequest(CategoryRequest categoryRequest);
}


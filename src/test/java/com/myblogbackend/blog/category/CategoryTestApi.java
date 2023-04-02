package com.myblogbackend.blog.category;

import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.request.CategoryRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import com.myblogbackend.blog.security.UserPrincipal;

import java.util.UUID;

public final class CategoryTestApi {


    public static UserPrincipal getUserInfo() {
        return new UserPrincipal(
                UUID.randomUUID(),
                "Test",
                "test@example.com",
                "xxx-xxx-xxx"
        );
    }

    public static CategoryEntity makeCategoryForSaving(UUID id) {
        return CategoryEntity.builder()
                .name("Category A")
                .build();
    }

    public static CategoryRequest prepareCategoryForRequesting() {
        return CategoryRequest.builder()
                .name("Category A")
                .build();
    }

    public static CategoryResponse prepareCategoryForResponse(final UUID id, final String name) {
        return CategoryResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static CategoryResponse createCategoryData() {
        return CategoryResponse.builder()
                .id(UUID.randomUUID())
                .name("Category A")
                .build();
    }


}

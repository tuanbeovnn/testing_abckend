package com.myblogbackend.blog.post;

import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.models.PostEntity;
import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.response.CategoryResponse;
import com.myblogbackend.blog.response.PostResponse;
import com.myblogbackend.blog.security.UserPrincipal;

import java.util.UUID;

public final class PostsTestApi {

    public static PostResponse makePostResponse(final UUID id, final String title, final String content) {
        return PostResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }

    public static PostRequest createPostRequestData() {
        return PostRequest.builder()
                .title("Post A")
                .content("Post A content")
                .build();
    }

    public static UserPrincipal getUserInfo() {
        return new UserPrincipal(
                UUID.randomUUID(),
                "Test",
                "test@example.com",
                "xxx-xxx-xxx"
        );
    }

    public static PostEntity preparePostForCreating() {
        return PostEntity.builder()
                .title("Post A")
                .content("Post A content")
                .build();
    }

    public static CategoryEntity prepareCategoryForCreating(final UUID uuid) {
        return CategoryEntity.builder()
                .id(uuid)
                .name("Category A")
                .build();
    }

    public static CategoryResponse createCategoryData() {
        return CategoryResponse.builder()
                .id(UUID.randomUUID())
                .name("Category A")
                .build();
    }


}

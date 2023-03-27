package com.myblogbackend.blog.services;

import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.response.PostResponse;

import java.util.List;
import java.util.Map;

public interface PostService {
    List<PostResponse> getAllPosts();
    Map<String, Object> getAllPostsPagination(String title, int page, int size);
    Map<String, Object> getAllPostsByCategoryId(Long categoryId, int page, int size);

    PostResponse getPostById(Long id);

    PostResponse createPost(PostRequest postRequest);


    PostResponse updatePost(Long id, PostRequest postRequest);

}

package com.myblogbackend.blog.services;

import com.myblogbackend.blog.models.PostEntity;
import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.response.PostResponse;

import java.util.List;

public interface PostService {
    List<PostResponse> getAllPosts();

    List<PostResponse> getAllPostsByCategoryId(Long categoryId);

    PostResponse getPostById(Long id);

    PostResponse createPost( PostRequest postRequest);


    PostResponse updatePost(Long id, PostRequest postRequest);

}

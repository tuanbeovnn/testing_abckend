package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.constant.ErrorMessage;
import com.myblogbackend.blog.exception.BlogLangException;
import com.myblogbackend.blog.mapper.PostMapper;
import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.models.PostEntity;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.repositories.PostRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.response.PostResponse;
import com.myblogbackend.blog.services.PostService;
import com.myblogbackend.blog.utils.JWTSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;
    private final PostMapper postMapper;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        var signedInUser = JWTSecurityUtil.getJWTUserInfo().orElseThrow();
        var category = validateCategory(postRequest.getCategoryId());
        var postEntity = postMapper.toPostEntity(postRequest);
        postEntity.setCategory(category);
        // populate owner
        postEntity.setUser(usersRepository.findById(signedInUser.getId()).orElseThrow());
        var createdPost = postRepository.save(postEntity);
        return postMapper.toPostResponse(createdPost);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        var postEntities = postRepository.findAll();
        return postMapper.toListPostResponse(postEntities);
    }

    @Override

    public Map<String, Object> getAllPostsPagination(String title, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<PostEntity> pagePosts;
        if (title == null) pagePosts = postRepository.findAll(paging);
        else
            pagePosts = postRepository.findByTitleContaining(title, paging);
        return getStringObjectMap(pagePosts);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(Page<PostEntity> pagePosts) {
        List<PostResponse> postResponseList = postMapper.toListPostResponse(pagePosts.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("posts", postResponseList);
        response.put("currentPage", pagePosts.getNumber());
        response.put("totalItems", pagePosts.getTotalElements());
        response.put("totalPages", pagePosts.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> getAllPostsByCategoryId(Long categoryId, int page, int size) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new BlogLangException(ErrorMessage.NOT_FOUND);
        }
        Pageable paging = PageRequest.of(page, size);
        Page<PostEntity> pagePostEntity = postRepository.findByCategoryId(categoryId, paging);
        return getStringObjectMap(pagePostEntity);
    }

    @Override
    public PostResponse getPostById(Long id) {
        var post = postRepository
                .findById(id)
                .orElseThrow(() -> new BlogLangException(ErrorMessage.NOT_FOUND));
        return postMapper.toPostResponse(post);
    }


    @Override
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BlogLangException(ErrorMessage.NOT_FOUND));
        var category = validateCategory(postRequest.getCategoryId());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        var updatedPost = postRepository.save(post);
        return postMapper.toPostResponse(updatedPost);
    }

    private CategoryEntity validateCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BlogLangException(ErrorMessage.NOT_FOUND));
    }
}

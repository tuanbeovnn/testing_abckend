package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.constant.ErrorMessage;
import com.myblogbackend.blog.exception.BlogLangException;
import com.myblogbackend.blog.mapper.PostMapper;
import com.myblogbackend.blog.models.CategoryEntity;
import com.myblogbackend.blog.pagination.OffsetPageRequest;
import com.myblogbackend.blog.pagination.PaginationPage;
import com.myblogbackend.blog.repositories.CategoryRepository;
import com.myblogbackend.blog.repositories.PostRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.response.PostResponse;
import com.myblogbackend.blog.services.PostService;
import com.myblogbackend.blog.utils.JWTSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;
    private final PostMapper postMapper;

    @Override
    public PostResponse createPost(final PostRequest postRequest) {
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
    public PaginationPage<PostResponse> getAllPostsByUserId(final Integer offset, final Integer limited, final UUID userId) {
        var pageable = new OffsetPageRequest(offset, limited);
        var postEntities = postRepository.findAllByUserId(userId, pageable);
        var postResponses = postEntities.getContent().stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
        return new PaginationPage<PostResponse>()
                .setRecords(postResponses)
                .setOffset(postEntities.getNumber())
                .setLimit(postEntities.getSize())
                .setTotalRecords(postEntities.getTotalElements());
    }

    @Override
    public PaginationPage<PostResponse> getAllPostsByCategoryId(final Integer offset, final Integer limited, final UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new BlogLangException(ErrorMessage.NOT_FOUND);
        }
        var pageable = new OffsetPageRequest(offset, limited);
        var posts = postRepository.findAllByCategoryId(pageable, categoryId);
        var postResponses = posts.getContent().stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());

        return new PaginationPage<PostResponse>()
                .setRecords(postResponses)
                .setOffset(posts.getNumber())
                .setLimit(posts.getSize())
                .setTotalRecords(posts.getTotalElements());
    }

    @Override
    public PostResponse getPostById(final UUID id) {
        var post = postRepository
                .findById(id)
                .orElseThrow(() -> new BlogLangException(ErrorMessage.NOT_FOUND));
        return postMapper.toPostResponse(post);
    }


    @Override
    public PostResponse updatePost(final UUID id, final PostRequest postRequest) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new BlogLangException(ErrorMessage.NOT_FOUND));
        var category = validateCategory(postRequest.getCategoryId());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        var updatedPost = postRepository.save(post);
        return postMapper.toPostResponse(updatedPost);
    }

    private CategoryEntity validateCategory(final UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BlogLangException(ErrorMessage.NOT_FOUND));
    }
}

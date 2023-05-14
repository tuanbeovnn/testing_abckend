package com.myblogbackend.blog.services;

import com.myblogbackend.blog.pagination.PaginationPage;
import com.myblogbackend.blog.request.CommentRequest;
import com.myblogbackend.blog.response.CommentResponse;

import java.util.UUID;

public interface CommentService {
    CommentResponse createComment(final CommentRequest commentRequest);

    PaginationPage<CommentResponse> getListCommentsByPostId(final Integer offset, final Integer limited, final UUID postId);
}

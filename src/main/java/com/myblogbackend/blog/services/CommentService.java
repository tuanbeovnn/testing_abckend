package com.myblogbackend.blog.services;

import com.myblogbackend.blog.request.CommentRequest;
import com.myblogbackend.blog.response.CommentResponse;

import java.util.List;

public interface CommentService {
   CommentResponse createComment(CommentRequest commentRequest);
   List<CommentResponse> getListComments();
}

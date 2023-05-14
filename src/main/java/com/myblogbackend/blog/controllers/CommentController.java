package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.controllers.route.CategoryRoutes;
import com.myblogbackend.blog.controllers.route.CommentRoutes;
import com.myblogbackend.blog.controllers.route.CommonRoutes;
import com.myblogbackend.blog.request.CommentRequest;
import com.myblogbackend.blog.response.CommentResponse;
import com.myblogbackend.blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommonRoutes.BASE_API + CommonRoutes.VERSION + CommentRoutes.BASE_URL)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(CommentRequest commentRequest) {
        var commentResponse = commentService.createComment(commentRequest);
        return ResponseEntity.ok(commentResponse);
    }
}

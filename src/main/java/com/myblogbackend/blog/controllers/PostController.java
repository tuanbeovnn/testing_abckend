package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.controllers.route.CommonRoutes;
import com.myblogbackend.blog.controllers.route.PostRoutes;
import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonRoutes.BASE_API + CommonRoutes.VERSION + PostRoutes.BASE_URL)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<?> getAllPosts() {
        var postList = postService.getAllPosts();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(value = "id") Long id) {
        var post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getAllPostsByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
        var postList = postService.getAllPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(value = "id") Long id,
                                        PostRequest postRequest) {
        var post = postService.updatePost(id, postRequest);
        return ResponseEntity.ok(post);
    }

}

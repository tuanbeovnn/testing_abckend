package com.myblogbackend.blog.controllers;

import com.electronwill.nightconfig.core.conversion.Path;
import com.myblogbackend.blog.controllers.route.CommonRoutes;
import com.myblogbackend.blog.controllers.route.PostRoutes;
import com.myblogbackend.blog.models.PostEntity;
import com.myblogbackend.blog.request.PostRequest;
import com.myblogbackend.blog.response.PostResponse;
import com.myblogbackend.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CommonRoutes.BASE_API + CommonRoutes.VERSION + PostRoutes.BASE_URL)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<?> getAllPosts() {
        List<PostResponse> postList = postService.getAllPosts();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(value = "id") Long id) {
        PostResponse post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getAllPostsByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
        List<PostResponse> postList = postService.getAllPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postList);
    }

  /*  @PostMapping()
    public ResponseEntity<?> createPost(PostRequest postRequest) {
        PostResponse post = postService.createPost(postRequest);
        return ResponseEntity.ok(post);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(value = "id") Long id,
                                        PostRequest postRequest) {
        PostResponse post = postService.updatePost(id, postRequest);
        return ResponseEntity.ok(post);
    }

}

package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.controllers.route.CommonRoutes;
import com.myblogbackend.blog.controllers.route.UserRoutes;
import com.myblogbackend.blog.request.SignUpFormRequest;
import com.myblogbackend.blog.response.ApiResponse;
import com.myblogbackend.blog.services.MinioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(CommonRoutes.BASE_API + CommonRoutes.VERSION + "/files")
@RequiredArgsConstructor
public class FilesController {

    private final MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<?> registerUser(@ModelAttribute MultipartFile file) {
        var upload = minioService.uploadFile(file);
        return ResponseEntity.ok(upload);
    }
}

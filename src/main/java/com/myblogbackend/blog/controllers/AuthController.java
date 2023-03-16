package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.dtos.LoginForm;
import com.myblogbackend.blog.dtos.SignUpForm;
import com.myblogbackend.blog.dtos.TokenRefreshRequest;
import com.myblogbackend.blog.response.ApiResponse;
import com.myblogbackend.blog.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        var jwtResponse = authService.userLogin(loginRequest);
        if (Objects.isNull(jwtResponse)) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User has been deactivated/locked !!"));
        }
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest, HttpServletRequest request) {
        var newUser = authService.registerUser(signUpRequest, request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully!"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        var jwtResponse = authService.refreshJwtToken(tokenRefreshRequest);
        return ResponseEntity.ok().body(jwtResponse);
    }

}
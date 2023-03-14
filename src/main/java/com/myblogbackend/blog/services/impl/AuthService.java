package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.dtos.LoginForm;
import com.myblogbackend.blog.dtos.SignUpForm;
import com.myblogbackend.blog.dtos.TokenRefreshRequest;
import com.myblogbackend.blog.dtos.UserResponse;
import com.myblogbackend.blog.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    JwtResponse userLogin(LoginForm loginFormRequest);

    UserResponse registerUser(SignUpForm signUpRequest, HttpServletRequest request);
    JwtResponse refreshJwtToken(TokenRefreshRequest tokenRefreshRequest);
}

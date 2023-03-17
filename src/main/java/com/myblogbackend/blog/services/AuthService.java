package com.myblogbackend.blog.services;

import com.myblogbackend.blog.request.LoginFormRequest;
import com.myblogbackend.blog.request.SignUpFormRequest;
import com.myblogbackend.blog.request.TokenRefreshRequest;
import com.myblogbackend.blog.response.UserResponse;
import com.myblogbackend.blog.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    JwtResponse userLogin(LoginFormRequest loginFormRequest);

    UserResponse registerUser(SignUpFormRequest signUpRequest, HttpServletRequest request);

    JwtResponse refreshJwtToken(TokenRefreshRequest tokenRefreshRequest);
}

package com.myblogbackend.blog.services;

import com.myblogbackend.blog.request.LogOutRequest;
import com.myblogbackend.blog.response.UserResponse;
import com.myblogbackend.blog.security.UserPrincipal;

public interface UserService {
    void logoutUser(LogOutRequest logOutRequest, UserPrincipal userPrincipal);

    UserResponse findUserById(Long id);

    UserResponse aboutMe();
}

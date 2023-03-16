package com.myblogbackend.blog.services;

import com.myblogbackend.blog.dtos.LogOutRequest;
import com.myblogbackend.blog.response.UserProfile;
import com.myblogbackend.blog.services.impl.UserPrincipal;

public interface UserService {
    void logoutUser(LogOutRequest logOutRequest, UserPrincipal userPrincipal);

    UserProfile findUserById(Long id);

    UserProfile aboutMe();
}

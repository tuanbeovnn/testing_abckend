package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.request.LogOutRequest;
import com.myblogbackend.blog.response.ApiResponse;
import com.myblogbackend.blog.response.UserResponse;
import com.myblogbackend.blog.security.CurrentUser;
import com.myblogbackend.blog.security.UserPrincipal;
import com.myblogbackend.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        UserResponse userProfile = userService.aboutMe();
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/byID/{id}")
    public ResponseEntity<?> getUserProfileById(@PathVariable(value = "id") Long id) {
        UserResponse userProfile = userService.findUserById(id);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/logout")
    public ResponseEntity<ApiResponse> logoutUser(@CurrentUser UserPrincipal currentUser,
                                                  @Valid @RequestBody LogOutRequest logOutRequest) {
        userService.logoutUser(logOutRequest, currentUser);
        return ResponseEntity.ok(new ApiResponse(true, "User has successfully logged out from the system!"));
    }

}
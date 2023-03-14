package com.myblogbackend.blog.controllers;

import com.myblogbackend.blog.dtos.LogOutRequest;
import com.myblogbackend.blog.event.OnUserLogoutSuccessEvent;
import com.myblogbackend.blog.exception.ResourceNotFoundException;
import com.myblogbackend.blog.exception.UserLogoutException;
import com.myblogbackend.blog.models.UserDeviceEntity;
import com.myblogbackend.blog.models.UserEntity;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.response.ApiResponse;
import com.myblogbackend.blog.response.UserProfile;
import com.myblogbackend.blog.services.CurrentUser;
import com.myblogbackend.blog.services.RefreshTokenService;
import com.myblogbackend.blog.services.UserDeviceService;
import com.myblogbackend.blog.services.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/me")
    public UserEntity getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return usersRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfile> getUserProfile(@RequestParam(value = "email", required = false) Optional<String> email) {
        List<UserProfile> userProfiles = new ArrayList<>();
        if (email.isPresent()) {
            UserEntity userEntity = usersRepository.findByEmail(email.get())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", email.get()));
            UserProfile userProfile = new UserProfile(userEntity.getId(), userEntity.getEmail(), userEntity.getName(), userEntity.getActive());
            userProfiles.add(userProfile);
        } else {
            List<UserEntity> userEntities = usersRepository.findAll();
            for (UserEntity u : userEntities) {
                UserProfile userProfile = new UserProfile(u.getId(), u.getEmail(), u.getName(), u.getActive());
                userProfiles.add(userProfile);
            }
        }
        return userProfiles;
    }

    @GetMapping("/byID/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserProfile getUserProfileById(@PathVariable(value = "id") Long id) {
        UserEntity userEntity = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserProfile userProfile = new UserProfile(userEntity.getId(), userEntity.getEmail(), userEntity.getName(), userEntity.getActive());

        return userProfile;
    }

    @PutMapping("/byID/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deactivateUserById(@PathVariable(value = "id") Long id) {
        UserEntity userEntity = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userEntity.deactivate();
        usersRepository.save(userEntity);
        return ResponseEntity.ok(new ApiResponse(true, "User deactivated successfully!"));
    }

    @PutMapping("/byID/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> activateUserById(@PathVariable(value = "id") Long id) {
        UserEntity userEntity = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userEntity.activate();
        usersRepository.save(userEntity);
        return ResponseEntity.ok(new ApiResponse(true, "User activated successfully!"));
    }

    @DeleteMapping("/byID/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "id") Long id) {
        UserEntity userEntity = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        usersRepository.delete(userEntity);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully!"));
    }

    @PutMapping("/logout")
    public ResponseEntity<ApiResponse> logoutUser(@CurrentUser UserPrincipal currentUser,
                                                  @Valid @RequestBody LogOutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDeviceEntity userDeviceEntity = userDeviceService.findByUserId(currentUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user "));
        refreshTokenService.deleteById(userDeviceEntity.getRefreshToken().getId());

        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser.getEmail(), logOutRequest.getToken(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return ResponseEntity.ok(new ApiResponse(true, "User has successfully logged out from the system!"));
    }

}
package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.dtos.LogOutRequest;
import com.myblogbackend.blog.event.OnUserLogoutSuccessEvent;
import com.myblogbackend.blog.exception.ResourceNotFoundException;
import com.myblogbackend.blog.exception.UserLogoutException;
import com.myblogbackend.blog.models.UserEntity;
import com.myblogbackend.blog.repositories.RefreshTokenRepository;
import com.myblogbackend.blog.repositories.UserDeviceRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.response.UserProfile;
import com.myblogbackend.blog.services.UserService;
import com.myblogbackend.blog.utils.JWTSecurityUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDeviceRepository userDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UsersRepository usersRepository;

    public UserServiceImpl(UserDeviceRepository userDeviceRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           ApplicationEventPublisher applicationEventPublisher, UsersRepository usersRepository) {
        this.userDeviceRepository = userDeviceRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.usersRepository = usersRepository;
    }

    @Override
    public void logoutUser(LogOutRequest logOutRequest, UserPrincipal currentUser) {
        var deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        var userDevice = userDeviceRepository.findByUserId(currentUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(),
                        "Invalid device Id supplied. No matching device found for the given user "));
        refreshTokenRepository.deleteById(userDevice.getRefreshToken().getId());
        var logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser.getEmail(), logOutRequest.getToken(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
    }

    @Override
    public UserProfile findUserById(Long id) {
        var userEntity = getUserById(id);
        return new UserProfile(userEntity.getId(), userEntity.getEmail(), userEntity.getName(), userEntity.getActive());
    }

    @Override
    public UserProfile aboutMe() {
        var signedInUser = JWTSecurityUtil.getJWTUserInfo().orElseThrow();
        var userEntity = getUserById(signedInUser.getId());
        return new UserProfile(userEntity.getId(), userEntity.getEmail(), userEntity.getName(), userEntity.getActive());
    }

    private UserEntity getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}

package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.event.OnUserLogoutSuccessEvent;
import com.myblogbackend.blog.exception.ResourceNotFoundException;
import com.myblogbackend.blog.exception.UserLogoutException;
import com.myblogbackend.blog.mapper.UserMapper;
import com.myblogbackend.blog.models.UserEntity;
import com.myblogbackend.blog.repositories.RefreshTokenRepository;
import com.myblogbackend.blog.repositories.UserDeviceRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.request.LogOutRequest;
import com.myblogbackend.blog.response.UserResponse;
import com.myblogbackend.blog.security.UserPrincipal;
import com.myblogbackend.blog.services.UserService;
import com.myblogbackend.blog.utils.JWTSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDeviceRepository userDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UsersRepository usersRepository;

    private final UserMapper userMapper;

    @Override
    public void logoutUser(final LogOutRequest logOutRequest, final UserPrincipal currentUser) {
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
    public UserResponse findUserById(final Long id) {
        var userEntity = getUserById(id);
        return userMapper.toUserDTO(userEntity);
    }

    @Override
    public UserResponse aboutMe() {
        var signedInUser = JWTSecurityUtil.getJWTUserInfo().orElseThrow();
        var userEntity = getUserById(signedInUser.getId());
        return userMapper.toUserDTO(userEntity);
    }

    private UserEntity getUserById(final Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}

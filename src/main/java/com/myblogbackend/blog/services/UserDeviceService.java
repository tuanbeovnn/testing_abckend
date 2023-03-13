package com.myblogbackend.blog.services;

import java.util.Optional;

import com.myblogbackend.blog.dtos.DeviceInfo;
import com.myblogbackend.blog.exception.TokenRefreshException;
import com.myblogbackend.blog.models.RefreshTokenEntity;
import com.myblogbackend.blog.models.UserDeviceEntity;
import com.myblogbackend.blog.repositories.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserDeviceService {

	@Autowired
    private UserDeviceRepository userDeviceRepository;

    public Optional<UserDeviceEntity> findByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    public Optional<UserDeviceEntity> findByRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        return userDeviceRepository.findByRefreshToken(refreshTokenEntity);
    }

    public UserDeviceEntity createUserDevice(DeviceInfo deviceInfo) {
        UserDeviceEntity userDeviceEntity = new UserDeviceEntity();
        userDeviceEntity.setDeviceId(deviceInfo.getDeviceId());
        userDeviceEntity.setDeviceType(deviceInfo.getDeviceType());
        userDeviceEntity.setIsRefreshActive(true);
        return userDeviceEntity;
    }

    public void verifyRefreshAvailability(RefreshTokenEntity refreshTokenEntity) {
        UserDeviceEntity userDeviceEntity = findByRefreshToken(refreshTokenEntity)
                .orElseThrow(() -> new TokenRefreshException(refreshTokenEntity.getToken(), "No device found for the matching token. Please login again"));

        if (!userDeviceEntity.getIsRefreshActive()) {
            throw new TokenRefreshException(refreshTokenEntity.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }
}
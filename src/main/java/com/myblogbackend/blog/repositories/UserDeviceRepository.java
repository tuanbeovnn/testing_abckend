package com.myblogbackend.blog.repositories;

import com.myblogbackend.blog.models.RefreshTokenEntity;
import com.myblogbackend.blog.models.UserDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, Long> {

    @Override
    Optional<UserDeviceEntity> findById(Long id);

    Optional<UserDeviceEntity> findByRefreshToken(RefreshTokenEntity refreshTokenEntity);

    Optional<UserDeviceEntity> findByUserId(Long userId);
}
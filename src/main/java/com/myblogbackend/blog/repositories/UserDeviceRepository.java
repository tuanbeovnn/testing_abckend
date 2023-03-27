package com.myblogbackend.blog.repositories;

import com.myblogbackend.blog.models.RefreshTokenEntity;
import com.myblogbackend.blog.models.UserDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, UUID> {

    Optional<UserDeviceEntity> findById(UUID id);

    Optional<UserDeviceEntity> findByRefreshToken(RefreshTokenEntity refreshTokenEntity);

    Optional<UserDeviceEntity> findByUserId(UUID userId);
}
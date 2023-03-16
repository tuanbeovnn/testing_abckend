package com.myblogbackend.blog.repositories;

import java.util.Optional;

import com.myblogbackend.blog.models.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findById(Long id);

    Optional<RefreshTokenEntity> findByToken(String token);

}
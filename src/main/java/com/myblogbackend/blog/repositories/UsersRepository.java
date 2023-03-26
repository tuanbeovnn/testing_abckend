package com.myblogbackend.blog.repositories;

import com.myblogbackend.blog.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}
package com.myblogbackend.blog.repositories;


import com.myblogbackend.blog.models.UserEntity;
import com.myblogbackend.blog.models.UserVerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserVerificationTokenEntity, Long> {
    UserVerificationTokenEntity findByVerificationToken(String verToken);

    UserVerificationTokenEntity findByUser(UserEntity user);
}

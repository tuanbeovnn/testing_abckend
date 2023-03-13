package com.myblogbackend.blog.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.myblogbackend.blog.exception.TokenRefreshException;
import com.myblogbackend.blog.models.RefreshTokenEntity;
import com.myblogbackend.blog.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RefreshTokenService {

	@Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity) {
        return refreshTokenRepository.save(refreshTokenEntity);
    }
    
    public RefreshTokenEntity createRefreshToken() {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(3600000));
        refreshTokenEntity.setToken(UUID.randomUUID().toString());
        refreshTokenEntity.setRefreshCount(0L);
        return refreshTokenEntity;
    }

    public void verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
    }

    public void deleteById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    public void increaseCount(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenEntity.incrementRefreshCount();
        save(refreshTokenEntity);
    }
}
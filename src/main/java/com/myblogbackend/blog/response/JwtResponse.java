package com.myblogbackend.blog.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private final String accessToken;
    private final String refreshToken;
    private final Long expiryDuration;
    private static final String tokenType = "Bearer ";

    public JwtResponse(final String accessToken, final String refreshToken, final Long expiryDuration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiryDuration = expiryDuration;
    }
}
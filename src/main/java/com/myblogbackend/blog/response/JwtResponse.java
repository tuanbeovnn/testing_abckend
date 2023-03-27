package com.myblogbackend.blog.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiryDuration;

    public JwtResponse(final String accessToken, final String refreshToken, final Long expiryDuration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiryDuration = expiryDuration;
        tokenType = "Bearer ";
    }
}
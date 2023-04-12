package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.constant.ErrorMessage;
import com.myblogbackend.blog.exception.BlogLangException;
import com.myblogbackend.blog.exception.TokenRefreshException;
import com.myblogbackend.blog.mapper.UserMapper;
import com.myblogbackend.blog.models.RefreshTokenEntity;
import com.myblogbackend.blog.models.UserDeviceEntity;
import com.myblogbackend.blog.models.UserEntity;
import com.myblogbackend.blog.repositories.RefreshTokenRepository;
import com.myblogbackend.blog.repositories.UserDeviceRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.request.DeviceInfoRequest;
import com.myblogbackend.blog.request.LoginFormRequest;
import com.myblogbackend.blog.request.SignUpFormRequest;
import com.myblogbackend.blog.request.TokenRefreshRequest;
import com.myblogbackend.blog.response.JwtResponse;
import com.myblogbackend.blog.response.UserResponse;
import com.myblogbackend.blog.security.JwtProvider;
import com.myblogbackend.blog.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final long ONE_HOUR_IN_MILLIS = 3600000;
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDeviceRepository userDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public JwtResponse userLogin(final LoginFormRequest loginRequest) {
        var userEntity = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User not found."));

        var authentication = authenticateUser(loginRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwtToken = jwtProvider.generateJwtToken(authentication);
        RefreshTokenEntity refreshTokenEntity = createRefreshToken(loginRequest, userEntity);
        return new JwtResponse(jwtToken, refreshTokenEntity.getToken(), jwtProvider.getExpiryDuration());
    }

    @Override
    public UserResponse registerUser(final SignUpFormRequest signUpRequest, final HttpServletRequest request) {
        var userEntity = usersRepository.findByEmail(signUpRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Fail -> Email is already in use!"));
        // Creating user's account
        userEntity.setName(signUpRequest.getName());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setPassword(encoder.encode(signUpRequest.getPassword()));
        userEntity.activate();
        UserEntity result = usersRepository.save(userEntity);
        return userMapper.toUserDTO(result);
    }

    @Override
    public JwtResponse refreshJwtToken(final TokenRefreshRequest tokenRefreshRequest) {
        var requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        var token = Optional.of(refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    verifyExpiration(refreshToken);
                    verifyRefreshAvailability(refreshToken);
                    increaseCount(refreshToken);
                    return refreshToken;
                })
                .map(RefreshTokenEntity::getUserDevice)
                .map(UserDeviceEntity::getUser)
                .map(jwtProvider::generateTokenFromUser)
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again")));
        return new JwtResponse(token.get(), tokenRefreshRequest.getRefreshToken(), jwtProvider.getExpiryDuration());
    }

    private void verifyExpiration(final RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
    }

    private void verifyRefreshAvailability(final RefreshTokenEntity refreshTokenEntity) {
        var userDeviceEntity = userDeviceRepository.findByRefreshToken(refreshTokenEntity)
                .orElseThrow(() -> new TokenRefreshException(refreshTokenEntity.getToken(), "No device found for the matching token. Please login again"));
        if (!userDeviceEntity.getIsRefreshActive()) {
            throw new TokenRefreshException(refreshTokenEntity.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }

    private void increaseCount(final RefreshTokenEntity refreshTokenEntity) {
        refreshTokenEntity.incrementRefreshCount();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private UserDeviceEntity createUserDevice(final DeviceInfoRequest deviceInfoRequest) {
        return UserDeviceEntity.builder()
                .deviceId(deviceInfoRequest.getDeviceId())
                .deviceType(deviceInfoRequest.getDeviceType())
                .isRefreshActive(true)
                .build();
    }

    private RefreshTokenEntity createRefreshToken() {
        return RefreshTokenEntity.builder()
                .expiryDate(Instant.now().plusMillis(ONE_HOUR_IN_MILLIS))
                .token(UUID.randomUUID().toString())
                .refreshCount(0L)
                .build();
    }

    private Authentication authenticateUser(final LoginFormRequest loginRequest) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BlogLangException(ErrorMessage.USER_NOT_AUTHORIZATION, e);
        }
    }

    private RefreshTokenEntity createRefreshToken(final LoginFormRequest loginRequest, final UserEntity userEntity) {
        userDeviceRepository.findByUserId(userEntity.getId())
                .map(UserDeviceEntity::getRefreshToken)
                .map(RefreshTokenEntity::getId)
                .ifPresent(refreshTokenRepository::deleteById);

        var userDeviceEntity = createUserDevice(loginRequest.getDeviceInfo());
        var refreshTokenEntity = createRefreshToken();
        userDeviceEntity.setUser(userEntity);
        userDeviceEntity.setRefreshToken(refreshTokenEntity);
        refreshTokenEntity.setUserDevice(userDeviceEntity);
        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
        return refreshTokenEntity;
    }
}

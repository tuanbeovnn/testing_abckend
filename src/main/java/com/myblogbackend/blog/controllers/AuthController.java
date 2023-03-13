package com.myblogbackend.blog.controllers;

import java.net.URI;
import java.util.Optional;


import com.myblogbackend.blog.dtos.LoginForm;
import com.myblogbackend.blog.dtos.SignUpForm;
import com.myblogbackend.blog.dtos.TokenRefreshRequest;
import com.myblogbackend.blog.exception.TokenRefreshException;
import com.myblogbackend.blog.models.*;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.response.ApiResponse;
import com.myblogbackend.blog.response.JwtResponse;
import com.myblogbackend.blog.response.UserIdentityAvailability;
import com.myblogbackend.blog.security.JwtProvider;
import com.myblogbackend.blog.services.RefreshTokenService;
import com.myblogbackend.blog.services.UserDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;
 
    @Autowired
    UsersRepository usersRepository;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private UserDeviceService userDeviceService;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
    	
    	UserEntity userEntity = usersRepository.findByEmail(loginRequest.getEmail())
    			.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User not found."));
    	
    	if (userEntity.getActive()) {
    		Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            ); 
            SecurityContextHolder.getContext().setAuthentication(authentication); 
            String jwtToken = jwtProvider.generateJwtToken(authentication);
            userDeviceService.findByUserId(userEntity.getId())
            .map(UserDeviceEntity::getRefreshToken)
            .map(RefreshTokenEntity::getId)
            .ifPresent(refreshTokenService::deleteById);

            UserDeviceEntity userDeviceEntity = userDeviceService.createUserDevice(loginRequest.getDeviceInfo());
            RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken();
            userDeviceEntity.setUser(userEntity);
            userDeviceEntity.setRefreshToken(refreshTokenEntity);
            refreshTokenEntity.setUserDevice(userDeviceEntity);
            refreshTokenEntity = refreshTokenService.save(refreshTokenEntity);
            return ResponseEntity.ok(new JwtResponse(jwtToken, refreshTokenEntity.getToken(), jwtProvider.getExpiryDuration()));
    	}
    	return ResponseEntity.badRequest().body(new ApiResponse(false, "User has been deactivated/locked !!"));
    }
 
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(usersRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
 
        // Creating user's account
        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.getName());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setPassword(encoder.encode(signUpRequest.getPassword()));
        userEntity.activate();
        UserEntity result = usersRepository.save(userEntity);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();
 
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully!"));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
    	
    	String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
    	
    	Optional<String> token = Optional.of(refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    refreshTokenService.verifyExpiration(refreshToken);
                    userDeviceService.verifyRefreshAvailability(refreshToken);
                    refreshTokenService.increaseCount(refreshToken);
                    return refreshToken;
                })
                .map(RefreshTokenEntity::getUserDevice)
                .map(UserDeviceEntity::getUser)
                .map(u -> jwtProvider.generateTokenFromUser(u))
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again")));
        return ResponseEntity.ok().body(new JwtResponse(token.get(), tokenRefreshRequest.getRefreshToken(), jwtProvider.getExpiryDuration()));
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !usersRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }
}
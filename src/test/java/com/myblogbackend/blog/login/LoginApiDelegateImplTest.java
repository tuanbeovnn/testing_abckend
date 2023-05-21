package com.myblogbackend.blog.login;

import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.models.RefreshTokenEntity;
import com.myblogbackend.blog.models.UserEntity;
import com.myblogbackend.blog.repositories.RefreshTokenRepository;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.security.JwtProvider;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static com.myblogbackend.blog.login.LoginTestApi.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void givenLoginData_whenSendData_thenReturnsJwtResponseCreated() throws Exception {
//        //mock user login data
//        var loginDataRequest = loginDataForSaving();
//        //mock user entity
//        var userId = UUID.randomUUID();
//        var userEntity = userEntityForSaving(userId);
//        //create UsernamePasswordAuthenticationToken
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                loginDataRequest.getEmail(),
//                loginDataRequest.getPassword());
//        //create the authentication
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        //set the authentication to SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        //create the Jwt token from JwtProvider
//        var jwtToken = jwtProvider.generateJwtToken(authentication);
//        //create the refresh token
//        var refreshToken = createRefreshTokenEntity(loginDataRequest, userEntity);
//        //create expiration from jwt provider
//        var expirationDuration = jwtProvider.getExpiryDuration();
//        //create jwt response after login successfully
//        var jwtResponse = jwtResponseForSaving(jwtToken, refreshToken.getToken(), expirationDuration);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signin")
//                        .content(IntegrationTestUtil.asJsonString(loginDataRequest))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(responseBody().containsObjectBody(jwtResponse, JwtResponse.class, objectMapper));
    }

    @Test
    public void givenLoginDataV2_whenSendData_thenReturnsJwtResponseCreated() throws Exception {
        // create user and save to database
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = userEntityForSaving(userId, passwordEncoder.encode("123456"));
        var userSignInRequest = loginDataForRequesting();

        // Mock findByEmail
        Mockito.when(usersRepository.findByEmail(userSignInRequest.getEmail())).thenReturn(Optional.of(userEntity));

        // Mock authentication result
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userSignInRequest.getEmail(), userSignInRequest.getPassword());

        // Mock authentication manager
        Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);

        // Set the authentication in SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = "mockJwtToken";
        // Mock JWT provider
        Mockito.when(jwtProvider.generateJwtToken(authentication)).thenReturn(jwtToken);

        // Mock and return a non-null refreshToken
        RefreshTokenEntity refreshToken = refreshTokenForSaving();

        Mockito.when(refreshTokenRepository.save(Mockito.any())).thenReturn(refreshToken);

        // Set the expiry duration to 3600000 milliseconds (1 hour)
        long expiryDuration = 3600000L;
        Mockito.when(jwtProvider.getExpiryDuration()).thenReturn(expiryDuration);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signin")
                        .content(IntegrationTestUtil.asJsonString(userSignInRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(jwtToken)))
                .andExpect(jsonPath("$.refreshToken", is(refreshToken.getToken())))
                .andExpect(jsonPath("$.expiryDuration", is(expiryDuration)));
    }

}

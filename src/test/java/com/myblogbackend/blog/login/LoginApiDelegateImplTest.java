package com.myblogbackend.blog.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblogbackend.blog.IntegrationTestUtil;
import com.myblogbackend.blog.repositories.UsersRepository;
import com.myblogbackend.blog.response.CategoryResponse;
import com.myblogbackend.blog.response.JwtResponse;
import com.myblogbackend.blog.security.JwtProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static com.myblogbackend.blog.ResponseBodyMatcher.responseBody;
import static com.myblogbackend.blog.login.LoginTestApi.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void givenLoginData_whenSendData_thenReturnsJwtResponseCreated() throws Exception {
        //mock user login data
        var loginDataRequest = loginDataForSaving();
        //mock user entity
        var userId = UUID.randomUUID();
        var userEntity = userEntityForSaving(userId);
        //create UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDataRequest.getEmail(),
                loginDataRequest.getPassword());
        //create the authentication
        Authentication authentication = authenticationManager.authenticate(authToken);
        //set the authentication to SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //create the Jwt token from JwtProvider
        var jwtToken = jwtProvider.generateJwtToken(authentication);
        //create the refresh token
        var refreshToken = createRefreshTokenEntity(loginDataRequest, userEntity);
        //create expiration from jwt provider
        var expirationDuration = jwtProvider.getExpiryDuration();
        //create jwt response after login successfully
        var jwtResponse = jwtResponseForSaving(jwtToken, refreshToken.getToken(), expirationDuration);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signin")
                        .content(IntegrationTestUtil.asJsonString(loginDataRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(responseBody().containsObjectBody(jwtResponse, JwtResponse.class, objectMapper));
    }
}

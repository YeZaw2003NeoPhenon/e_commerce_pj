package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.dto.LoginRequest;
import com.example.E_commerce_application.dto.UserRequest;
import com.example.E_commerce_application.dto.UserResponse;
import com.example.E_commerce_application.jwt.JwtUtils;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.Role;
import com.example.E_commerce_application.model.User;
import com.example.E_commerce_application.security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private EntityConverter<User, UserResponse> userEntityConverter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService userService; /// under the test

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User user;

    private UserRequest userRequest;

    private UserResponse userResponse;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp(){
        user = new User("Neo","neo@gmail.com","neoneo", Role.ADMIN);
        userRequest = new UserRequest(user.getName(),user.getEmail(),user.getPassword(), user.getRole());
        userResponse = new UserResponse(user.getName(),user.getEmail(),user.getPassword(), user.getRole());
        loginRequest = new LoginRequest(user.getEmail(),user.getPassword());
    }

    @Test
    void testUserRegisteration(){
        when(userDao.save(any(User.class))).thenReturn(user);
        when(userEntityConverter.entityToDto(any(User.class), any())).thenReturn(userResponse);

        userResponse = userService.registerUser(userRequest);
        assertThat(userResponse.getName()).isEqualTo(user.getName());
        assertThat(userResponse.getEmail()).isEqualTo(user.getEmail());
        verify(userDao).save(any(User.class));
    }

    @Test
    void testLogin() {
        Authentication authentication = mock(Authentication.class);
        AppUser mockedAppUser = mock(AppUser.class);
        user.setId(1L);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockedAppUser);
        when(mockedAppUser.getUser()).thenReturn(user);
        when(mockedAppUser.getUsername()).thenReturn(user.getEmail());

        String testedToken = "fake-jwt-token";
        when(jwtUtils.generateToken(anyLong(),anyString(),any(Role.class))).thenReturn(testedToken);

        String token = userService.login(loginRequest);

        assertThat(token).isEqualTo(testedToken);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(user.getId(),user.getEmail(),user.getRole());
    }

}